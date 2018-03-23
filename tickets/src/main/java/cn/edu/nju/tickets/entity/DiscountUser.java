package cn.edu.nju.tickets.entity;

import javax.persistence.*;

@Entity
@Table(name = "discount_user")
public class DiscountUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_email")
    private String email;
    @Column(name = "discount_id")
    private int discountID;
    @Column(name = "own_num")
    private int ownNum;

    public DiscountUser() {
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
}
