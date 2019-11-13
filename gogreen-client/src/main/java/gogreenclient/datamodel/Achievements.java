package gogreenclient.datamodel;

import java.time.LocalDateTime;

public class Achievements {

    private Long id;

    private String userName;

    private String achievement;

    private LocalDateTime achieveData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public LocalDateTime getAchieveData() {
        return achieveData;
    }

    public void setAchieveData(LocalDateTime achieveData) {
        this.achieveData = achieveData;
    }

    @Override
    public String toString() {
        return "Achievements{"
            + "id=" + id
            + ", userName='" + userName + '\''
            + ", achievement='" + achievement + '\''
            + ", achieveData=" + achieveData
            + '}';
    }
}
