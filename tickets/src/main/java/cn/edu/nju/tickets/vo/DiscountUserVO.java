package cn.edu.nju.tickets.vo;

public class DiscountUserVO {
    private Integer id;
    private String email;
    private int discountID;
    private int ownNum;
    private int discountNum;
    private String startDate;
    private String endDate;

    public DiscountUserVO() {
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

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public int getOwnNum() {
        return ownNum;
    }

    public void setOwnNum(int ownNum) {
        this.ownNum = ownNum;
    }

    public int getDiscountNum() {
        return discountNum;
    }

    public void setDiscountNum(int discountNum) {
        this.discountNum = discountNum;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
