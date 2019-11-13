package gogreenserver.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tree_add")
public class Tree {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tree_id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "tree", nullable = true)
    private String tree;

    @Column(name = "date", nullable = true)
    private LocalDateTime addingDate;

    @Column(name = "co2_saved", nullable = true)
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
