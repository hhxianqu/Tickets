package cn.edu.nju.tickets.vo;

public class PerformVO {
    private Integer id;
    private String performName;
    private String startTime;
    private String endTime;
    private String type;
    private String venueName;
    private String city;
    private String detailPosition;
    private int normalPrice;
    private int vipPrice;
    private int vipNum;
    private int normalNum;
    private int vipSale;
    private int normalSale;
    private boolean settled;

    public PerformVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerformName() {
        return performName;
    }

    public void setPerformName(String performName) {
        this.performName = performName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
    }

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getVipNum() {
        return vipNum;
    }

    public void setVipNum(int vipNum) {
        this.vipNum = vipNum;
    }

    public int getNormalNum() {
        return normalNum;
    }

    public void setNormalNum(int normalNum) {
        this.normalNum = normalNum;
    }

    public int getVipSale() {
        return vipSale;
    }

    public void setVipSale(int vipSale) {
        this.vipSale = vipSale;
    }

    public int getNormalSale() {
        return normalSale;
    }

    public void setNormalSale(int normalSale) {
        this.normalSale = normalSale;
    }

    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }
}
