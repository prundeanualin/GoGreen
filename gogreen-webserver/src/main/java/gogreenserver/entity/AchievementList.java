package gogreenserver.entity;

import java.util.ArrayList;
import java.util.List;

public class AchievementList {

    private List<Achievements> achievementsList;

    public AchievementList() {
        this.achievementsList = new ArrayList<>();
    }

    public List<Achievements> getAchievementsList() {
        return achievementsList;
    }

    public void setAchievementsList(List<Achievements> achievementsList) {
        this.achievementsList = achievementsList;
    }
}
