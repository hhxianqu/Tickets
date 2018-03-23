package cn.edu.nju.tickets.vo;

import java.util.List;

public class OrderRegisterVO {

    private int performID;
    private int vipNum;
    private int normalNum;
    private int totalPrice;
    private List<Integer> seat;
    private int discountID;

    public OrderRegisterVO() {
    }

    public int getPerformID() {
        return performID;
    }

    public void setPerformID(int performID) {
        this.performID = performID;
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

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Integer> getSeat() {
        return seat;
    }

    public void setSeat(List<Integer> seat) {
        this.seat = seat;
    }

    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }
}
