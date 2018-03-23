package cn.edu.nju.tickets.vo;

import java.util.List;

public class OrderVO {

    private Integer id;
    private String performName;
    private String performStartTime;
    private String performVenue;
    private int vipNum;
    private int normalNum;
    private String state;
    private String createTime;
    private int price;
    private List<String> seat;

    public OrderVO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPerformName(String performName) {
        this.performName = performName;
    }

    public String getPerformName() {
        return performName;
    }

    public String getPerformVenue() {
        return performVenue;
    }

    public void setPerformVenue(String performVenue) {
        this.performVenue = performVenue;
    }

    public String getPerformStartTime() {
        return performStartTime;
    }

    public void setPerformStartTime(String performStartTime) {
        this.performStartTime = performStartTime;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public List<String> getSeat() {
        return seat;
    }

    public void setSeat(List<String> seat) {
        this.seat = seat;
    }
}
