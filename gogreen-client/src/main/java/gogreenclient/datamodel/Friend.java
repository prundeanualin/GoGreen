package gogreenclient.datamodel;

import java.time.LocalDateTime;

public class Friend {

    private Long id;

    private LocalDateTime addTime;

    private String userName;

    private String friendName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    @Override
    public String toString() {
        return "Friend{"
            + "id=" + id
            + ", addTime=" + addTime
            + ", userName='" + userName + '\''
            + ", friendName='" + friendName + '\''
            + '}';
    }
}
