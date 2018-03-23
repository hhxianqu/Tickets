package cn.edu.nju.tickets.vo;

public class SeatVO {
    private Integer id;
    private int performID;
    private int seatNum;
    private Boolean checked;

    public SeatVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getPerformID() {
        return performID;
    }

    public void setPerformID(int performID) {
        this.performID = performID;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
