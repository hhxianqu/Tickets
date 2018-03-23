package cn.edu.nju.tickets.vo;

public class PerformRegisterVO {
    private Integer performID;
    private String performName;
    private String performType;
    private String startDate;
    private String endDate;
    private int vipNum;
    private int vipPrice;
    private int normalNum;
    private int normalPrice;
    private String venueNum;

    public PerformRegisterVO() {
    }

    public Integer getPerformID() {
        return performID;
    }

    public void setPerformID(Integer performID) {
        this.performID = performID;
    }

    public String getPerformName() {
        return performName;
    }

    public void setPerformName(String performName) {
        this.performName = performName;
    }

    public String getPerformType() {
        return performType;
    }

    public void setPerformType(String performType) {
        this.performType = performType;
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

    public int getVipNum() {
        return vipNum;
    }

    public void setVipNum(int vipNum) {
        this.vipNum = vipNum;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getNormalNum() {
        return normalNum;
    }

    public void setNormalNum(int normalNum) {
        this.normalNum = normalNum;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

    public String getVenueNum() {
        return venueNum;
    }

    public void setVenueNum(String venueNum) {
        this.venueNum = venueNum;
    }
}
