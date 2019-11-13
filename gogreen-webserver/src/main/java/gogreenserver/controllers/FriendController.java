package gogreenserver.controllers;

import gogreenserver.entity.Friend;
import gogreenserver.entity.Records;
import gogreenserver.entity.User;
import gogreenserver.services.FriendService;
import gogreenserver.services.UserService;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class FriendController {

    private FriendService friendService;
    private UserService userService;
    private Logger logger;

    /**
     * Constructor of this class.
     * 
     * @param friendService friendService.
     * @param userService   userService.
     * @param logger        logger.
     */
    @Autowired
    public FriendController(FriendService friendService, UserService userService, Logger logger) {
        this.friendService = friendService;
        this.userService = userService;
        this.logger = logger;
    }

    /**
     * This end point serves for finding all of the friends of a user. If this user
     * does not has any friend then a NOT_FOUND will be returned.
     *
     * @param userName user name of who you want to find its friends.
     * @return a response entity of type list of friends.
     */
    @GetMapping(value = "/friend/{userName}")
    public ResponseEntity<List<Friend>> findAllFriendsByUserName(@PathVariable String userName,
            Authentication auth) {

        logger.debug("GET /friend/" + userName + " accessed by: " + auth.getName());

        List<Friend> list = friendService.findFriendsByUserName(userName);
        logger.debug(list);
        return new ResponseEntity<>(list, (list.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    /**
     * This end point serves for finding all records of a user's friends. If one or
     * more than one of its friends dont have a record then a INTERAL_SERVER_ERROR
     * will be return. If this user does not have any friend then a NOT_FOUND will
     * be return. Client side should handle these two error status.
     *
     * @param userName user name of who you want to find its friends' records.
     * @return a list of response entity of type records.
     */
    @GetMapping(value = "/friend/record/{userName}")
    public ResponseEntity<List<Records>> findFriendsRecordsByUserName(@PathVariable String userName,
            @RequestHeader(name = "limit", defaultValue = "10") int limit, Authentication auth) {

        logger.debug("GET /friend/" + userName + " accessed by: " + auth.getName());

        List<Records> list = new ArrayList<>();
        try {
            list = friendService.findFriendsRecordbyUserName(userName, limit);
        } catch (IOException e) {
            return new ResponseEntity<>(list, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders friendAmount = new HttpHeaders();
        friendAmount.set("friendAmount", String.valueOf(list.size()));

        return new ResponseEntity<>(list, friendAmount,
                (list.isEmpty()) ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }

    /**
     * This endpoint serves for adding a user as your friend. If that user does not
     * exist in the database, a NOT_FOUND will be returned. If that user exists but
     * adding fails, a INTERNAL_ SERVER_ERROR will be returned. If that user already
     * is your friend then a ALREADY_REPORTED will be returned.
     *
     * @param friend friend object
     * @return a response entity of type string.
     */
    @PostMapping(value = "/friend")
    public ResponseEntity<String> addFriend(@RequestBody Friend friend, Authentication auth) {

        logger.debug("POST /friend/ accessed by: " + auth.getName());

        if (!auth.getName().equals(friend.getUserName())) {
            new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        User user = userService.findById(friend.getFriendName()).orElse(null);
        if (user == null) {
            return new ResponseEntity<>("User " + friend.getFriendName() + " not found",
                    HttpStatus.NOT_FOUND);
        }
        if (friendService.friendshipExists(friend.getUserName(), friend.getFriendName())) {
            return new ResponseEntity<>("friendship already exists", HttpStatus.ALREADY_REPORTED);
        }
        friendService.addFriend(friend);
        return new ResponseEntity<>("successfully added friend", HttpStatus.OK);
    }
}
