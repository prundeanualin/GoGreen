package gogreenserver.services;

import gogreenserver.entity.Friend;
import gogreenserver.entity.Records;
import gogreenserver.repositories.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FriendService {

    private FriendRepository friendRepository;
    private RecordsService recordsService;

    @Autowired
    public FriendService(FriendRepository repository, RecordsService recordsService) {
        this.friendRepository = repository;
        this.recordsService = recordsService;
    }

    /**
     * Find all of one user's friends.
     *
     * @param userName user name of that user.
     * @return list of friends.
     */
    public List<Friend> findFriendsByUserName(String userName) {
        return friendRepository.findAllByUserName(userName);
    }

    /**
     * Find a user's friends' records by it's username.
     *
     * @param userName username of the user.
     * @return a list of records.
     * @throws IOException All of the users in the database should have a record, otherwise
     *                     something wrong in the database. This exception need to be handled
     *                     in the controller.
     */
    public List<Records> findFriendsRecordbyUserName(String userName,
                                                     int limit) throws IOException {
        List<Friend> list = findFriendsByUserName(userName);
        List<Records> result = new ArrayList<>();
        if (list.isEmpty()) {
            return result;
        } else {
            for (Friend f : list) {
                Records temp = recordsService.findById(f.getFriendName()).orElse(null);
                if (temp == null) {
                    throw new IOException("Database error. Friend's record not found.");
                }
                result.add(temp);
            }
            return result
                .stream()
                .sorted(Comparator.comparing(Records::getSavedCo2Total).reversed())
                .limit(10)
                .collect(Collectors.toList());
        }
    }

    /**
     * Create a friend relationship in the database.
     *
     * @param friend user's friend.
     * @return a friend object.
     */
    public Friend addFriend(Friend friend) {
        return friendRepository.saveAndFlush(friend);
    }

    /**
     * Find if you have added a user as your friend.
     *
     * @param userName   your user name.
     * @param friendName user name of whom you want to check.
     * @return true, if he is your friend. otherwise, he is not your friend.
     */
    public boolean friendshipExists(String userName, String friendName) {
        return friendRepository.findByUserNameAndFriendName(userName, friendName) != null;
    }

}
