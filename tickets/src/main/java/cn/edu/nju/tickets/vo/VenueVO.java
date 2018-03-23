package cn.edu.nju.tickets.vo;

public class VenueVO {
    private String city;
    private String detailPosition;
    private int seat;
    private String venueNumber;
    private String venueName;
    private String venueEmail;
    private Integer id;
    private int accountID;
    private int balance;

    public VenueVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public void setSeat(int deat) {
        this.seat = deat;
    }

    public String getVenueNumber() {
        return venueNumber;
    }

    public void setVenueNumber(String venueNumber) {
        this.venueNumber = venueNumber;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
