package gogreenserver.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "add_solarpanels")
public class AddSolarpanels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "panel_id")
    private Long id;

    @Column(name = "username")
    private String userName;

    @Column(name = "m2")
    private Float area;

    @Column(name = "date", nullable = true)
    private LocalDate date;


    @Column(name = "produced_kwh", nullable = true)
    private Float producedKwh;

    // Define Getters/Setters for JACKSON

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Float getArea() {
        return area;
    }

    public void setArea(Float area) {
        this.area = area;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getProducedKwh() {
        return producedKwh;
    }

    public void setProducedKwh(Float producedKwh) {
        this.producedKwh = producedKwh;
    }

    // Define toString

    @Override
    public String toString() {
        return "AddSolarpanels{"
            + "userName='" + userName + '\''
            + ", area=" + area
            + ", date=" + date
            + ", id=" + id
            + ", producedKwh=" + producedKwh
            + '}';
    }
}
