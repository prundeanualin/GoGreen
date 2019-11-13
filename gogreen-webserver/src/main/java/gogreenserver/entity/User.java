package gogreenserver.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    // Define fields
    @Id
    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "bdate")
    private LocalDate bdate;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "profile_pic_url", nullable = true)
    private String pfpUrl;

    // define constructors
    public User() {

    }

    /**
     * Constructor for User.
     *
     * @param username    Primary Key.
     * @param password    Password.
     * @param email       Email.
     * @param bdate       Birthdate of user.
     * @param dateCreated date created of account.
     */
    public User(String username, String password, String email, LocalDate bdate,
            LocalDate dateCreated, String pfpUrl) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.bdate = bdate;
        this.dateCreated = dateCreated;
        this.pfpUrl = pfpUrl;
    }

    // define getters and setters

    public LocalDate getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDate dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDate getBdate() {
        return bdate;
    }

    public void setBdate(LocalDate bdate) {
        this.bdate = bdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPfpUrl() {
        return pfpUrl;
    }

    public void setPfpUrl(String pfpUrl) {
        this.pfpUrl = pfpUrl;
    }

    @Override
    public String toString() {
        return "User{" + "userName='" + username + '\'' + ", email='" + email + '\''
                + ", bdate='" + bdate + '\'' + ", dateCreated='" + dateCreated + '\'' + '}';
    }
}
