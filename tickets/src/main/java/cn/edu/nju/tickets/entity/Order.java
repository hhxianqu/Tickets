package cn.edu.nju.tickets.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "perform_id")
    private int performID;
    private String email;
    @Column(name = "vip_num")
    private int vipNum;
    @Column(name = "normal_num")
    private int normalNum;
    @Column(name = "order_state")
    private String state;
    @Column(name = "createTime")
    private Date createTime;
    private int price;

    public Order() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
