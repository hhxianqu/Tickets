package cn.edu.nju.tickets.vo;

public class DiscountMarketVO {
    private Integer id;
    private int discountNum;
    private String startDate;
    private String endDate;
    private int scoreToDeduct;

    public DiscountMarketVO() {
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

    public int getScoreToDeduct() {
        return scoreToDeduct;
    }

    public void setScoreToDeduct(int scoreToDeduct) {
        this.scoreToDeduct = scoreToDeduct;
    }
}
