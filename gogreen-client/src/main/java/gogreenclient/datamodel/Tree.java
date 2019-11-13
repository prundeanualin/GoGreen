package gogreenclient.datamodel;

import java.time.LocalDateTime;


public class Tree {

    private String userName;

    private String tree;

    private LocalDateTime addingDate;

    private float co2Saved;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    public LocalDateTime getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(LocalDateTime addingDate) {
        this.addingDate = addingDate;
    }

    public float getCo2Saved() {
        return co2Saved;
    }

    public void setCo2Saved(float co2Saved) {
        this.co2Saved = co2Saved;
    }

    @Override
    public String toString() {
        return "Tree{"
            + "userName='" + userName + '\''
            + ", tree='" + tree + '\''
            + ", addingDate=" + addingDate
            + ", co2Saved=" + co2Saved
            + '}';
    }
}
