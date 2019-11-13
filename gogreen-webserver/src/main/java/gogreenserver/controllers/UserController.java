package gogreenserver.controllers;

import gogreenserver.entity.User;
import gogreenserver.services.UserService;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.ws.rs.Consumes;

@RestController
@RequestMapping("/api")
public class UserController {
    private final Logger logger;
    private UserService userService;
    private BCryptPasswordEncoder encoder;

    /**
     * Autowired constructor, do not touch.
     */
    @Autowired
    public UserController(UserService userService, Logger logger, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.logger = logger;
        this.encoder = encoder;
    }

    @GetMapping("/login")
    public void login() {
        logger.debug("GET /login/ accessed");
    }

    /**
     * This method will go to database and try to find the user by the user name.
     *
     * @param userName user name.
     * @return if userName is found in the database, it will return a ResponseEntity
     *         which body is "success", otherwise another entity will be returned
     *         with the body being "fail".
     */
    @GetMapping("/user/findUser/{user_name}")
    public ResponseEntity<String> findUser(@PathVariable("user_name") String userName) {
        logger.debug("GET /user/findusers/" + userName + "/ accessed");
        User user = userService.findById(userName).orElse(null);
        if (user != null) {
            return new ResponseEntity<String>("success", HttpStatus.OK);
        }
        return new ResponseEntity<String>("fail", HttpStatus.NOT_FOUND);
    }

    /**
     * Returns the url to be used a profile picture.
     * 
     * @param username the user to use.
     */
    @GetMapping("/user/photourl/{user}")
    public ResponseEntity<String> getPicUrl(@PathVariable("user") String username,
            Authentication auth) {

        logger.debug("GET /user/photourl/" + username + " accessed by :" + auth.getName());

        Optional<User> user = userService.findById(username);
        return new ResponseEntity<String>(user.isPresent() ? user.get().getPfpUrl() : "",
                user.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /**
     * . Endpoint controller for returning user profile picture from the server
     * 
     * @param username User name
     * @return User's profile picture
     * @throws IOException exception
     */
    @GetMapping("/user/photo/{username}")
    @ResponseBody
    public ResponseEntity<File> getPicFromServer(@PathVariable("username") String username)
            throws IOException {
        logger.debug("GET/user/photo/" + username + "/ accessed");
        String pathname = "gogreen-webserver/src/main/profile_pictures/" + username + ".png";
        File file = new File(pathname);
        boolean exists = file.exists();
        if (exists) {
            return new ResponseEntity<File>(file, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    /**
     * This method creates a new User entry in the "User" table.
     */
    @PostMapping("/createUser")
    public ResponseEntity<Object> addUser(@RequestBody User theUser) {
        logger.debug("POST /createUser/ accessed");

        if (userService.findById(theUser.getUsername()).isPresent()) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }

        userService.createUser(theUser);
        return new ResponseEntity<>(theUser, HttpStatus.OK);
    }

    /**
     * This method updates a single property of a user.
     * 
     * @param username The user in question.
     * @param property either "email", "password", "birthdate" or "photo".
     */
    @PostMapping("/updateUser/{username}/{property}")
    public ResponseEntity<String> updateUser(@PathVariable("username") String username,
            @PathVariable("property") String property, @RequestBody String body,
            Authentication auth) {

        logger.debug("POST /updateUser/" + username + "/" + property + "/ accessed by: "
                + auth.getName());

        if (!auth.getName().equals(username)) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        // This can never be null, because otherwise you wouldn't be able to
        // authenticate in
        // the first place
        User user = userService.findById(username).get();

        logger.debug(body);
        switch (property) {
            case "email":
                user.setEmail(body);
                break;
            case "password":
                user.setPassword(encoder.encode(body));
                break;
            case "birthday":
                // for some reason, the local date is sent with quotes.
                try {
                    user.setBdate(
                            LocalDate.parse(body, DateTimeFormatter.ofPattern("\"yyyy-MM-dd\"")));
                } catch (DateTimeException e) {
                    logger.catching(e);
                    return new ResponseEntity<>("Invalid date format.",
                            HttpStatus.BAD_REQUEST);
                }
                break;
            case "photo":
                user.setPfpUrl(body);
                break;
            default:
                return new ResponseEntity<>("Invalid user property",
                        HttpStatus.NOT_FOUND);
        }

        userService.updateUser(user);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Receives a multipart file object from client (client profile photo) and
     * eventually saves it as an image on the server side in
     * gogreen-webserver/src/main/User_photos .
     * 
     * @param file     the photo user picked in multipart file format
     * @param userName name of the user
     * @return the response of the http exchange
     * @throws IOException error while saving the image
     */
    @PostMapping("/createUser/upload")
    @Consumes("multipart/form-data")
    public ResponseEntity<String> uploadPhoto(@RequestParam("profile_pic") MultipartFile file,
            @RequestParam("username") String userName) {

        logger.debug("POST /createUser/upload/" + userName);
        try {
            userService.saveProfilePicture(file, userName);
        } catch (IOException e) {
            logger.error("Error saving photo", e);
            return new ResponseEntity<>("cannot save picture", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    /**
     * Remove the user from existence.
     *
     * @param user The user username.
     */
    @DeleteMapping("/deleteUser/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable("username") String user,
            Authentication auth) {
        if (!user.equals(auth.getName())) {
            return new ResponseEntity<String>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }
        logger.debug("DELETE /user/" + user + "/ accessed by " + auth.getName());
        userService.deleteUser(user);
        new File("gogreen-webserver/src/main/profile_pictures/" + user + ".png").delete();
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}