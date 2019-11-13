package gogreenclient.datamodel;

import java.time.LocalDate;

public class AddSolarpanels  {

    private String userName;
    private Float area;
    private LocalDate date;
    private Long id;
    private Float producedKwh;


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
