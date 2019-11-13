package gogreenserver.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_achieved")
public class Achievements {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "achieve_id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "achievement")
    private String achievement;

    @Column(name = "achieved_data")
    private LocalDateTime achieveData;

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
