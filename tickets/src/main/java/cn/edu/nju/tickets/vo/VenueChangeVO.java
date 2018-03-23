package cn.edu.nju.tickets.vo;

public class VenueChangeVO {

    private String email;
    private String venueName;
    private String city;
    private String detailPosition;
    private int seat;
    private String venueNum;

    public VenueChangeVO() {
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
}
