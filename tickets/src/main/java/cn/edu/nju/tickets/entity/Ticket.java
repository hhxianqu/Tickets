package cn.edu.nju.tickets.entity;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "vip_num")
    private int vipNum;
    @Column(name = "normal_num")
    private int normalNum;
    @Column(name = "vip_price")
    private int vipPrice;
    @Column(name = "normal_price")
    private int normalPrice;
    @Column(name = "vip_sale")
    private int vipSale;
    @Column(name = "normal_sale")
    private int normalSale;
    private Boolean settled;

    public Ticket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public int getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(int vipPrice) {
        this.vipPrice = vipPrice;
    }

    public int getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(int normalPrice) {
        this.normalPrice = normalPrice;
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

    public Boolean getSettled() {
        return settled;
    }

    public void setSettled(Boolean settled) {
        this.settled = settled;
    }
}
