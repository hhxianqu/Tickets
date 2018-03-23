package cn.edu.nju.tickets.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "discount_market")
public class DiscountMarket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "discount_num")
    private int discountNum;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "score_to_deduct")
    private int scoreToDeduct;

    public DiscountMarket() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getDiscountNum() {
        return discountNum;
    }

    public void setDiscountNum(int discountNum) {
        this.discountNum = discountNum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getScoreToDeduct() {
        return scoreToDeduct;
    }

    public void setScoreToDeduct(int scoreToDeduct) {
        this.scoreToDeduct = scoreToDeduct;
    }
}
