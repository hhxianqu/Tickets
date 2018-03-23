package cn.edu.nju.tickets.entity;

import javax.persistence.*;

@Entity
@Table(name = "venue")
public class Venue {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "venue_name")
    private String venueName;

    @Column(name = "venue_password")
    private String venuePassword;

    @Column(name = "venue_seat")
    private int seat;

    @Column(name = "venue_number")
    private String venueNumber;

    @Column(name = "venue_city")
    private String city;

    @Column(name = "venue_detail_position")
    private String detailPosition;

    private boolean audited;

    private boolean passed;

    @Column(name = "venue_email")
    private String venueEmail;

    @Column(name = "account_id")
    private int accountID;

    public Venue() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getVenuePassword() {
        return venuePassword;
    }

    public void setVenuePassword(String venuePassword) {
        this.venuePassword = venuePassword;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getVenueNumber() {
        return venueNumber;
    }

    public void setVenueNumber(String venueNumber) {
        this.venueNumber = venueNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDetailPosition() {
        return detailPosition;
    }

    public void setDetailPosition(String detailPosition) {
        this.detailPosition = detailPosition;
    }

    public boolean isAudited() {
        return audited;
    }

    public void setAudited(boolean audited) {
        this.audited = audited;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getVenueEmail() {
        return venueEmail;
    }

    public void setVenueEmail(String venueEmail) {
        this.venueEmail = venueEmail;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
}
