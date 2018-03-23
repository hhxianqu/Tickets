package cn.edu.nju.tickets.entity;


import javax.persistence.*;

@Entity
@Table(name = "changed_venue")
public class ChangedVenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    @Column(name = "venue_name")
    private String venueName;
    private String city;
    @Column(name = "detail_position")
    private String detailPosition;
    private int seat;
    @Column(name = "venue_number")
    private String venueNum;
    private Boolean audit;
    private Boolean pass;

    public ChangedVenue() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
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

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getVenueNum() {
        return venueNum;
    }

    public void setVenueNum(String venueNum) {
        this.venueNum = venueNum;
    }

    public Boolean getAudit() {
        return audit;
    }

    public void setAudit(Boolean audit) {
        this.audit = audit;
    }

    public Boolean getPass() {
        return pass;
    }

    public void setPass(Boolean pass) {
        this.pass = pass;
    }
}
