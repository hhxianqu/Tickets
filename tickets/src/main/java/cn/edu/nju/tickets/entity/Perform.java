package cn.edu.nju.tickets.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "perform")
public class Perform {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(name = "perform_name")
    private String performName;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "venue_number")
    private String venueNum;
    @Column(name = "ticket_id")
    private int ticketID;
    @Column(name = "perform_type")
    private String type;
    @Column(name = "perform_audit")
    private boolean performAudit;
    @Column(name = "perform_pass")
    private boolean performPass;

    public Perform() {
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getPerformName() {
        return performName;
    }

    public void setPerformName(String performName) {
        this.performName = performName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getVenueNum() {
        return venueNum;
    }

    public void setVenueNum(String venueNum) {
        this.venueNum = venueNum;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isPerformAudit() {
        return performAudit;
    }

    public void setPerformAudit(boolean performAudit) {
        this.performAudit = performAudit;
    }

    public boolean isPerformPass() {
        return performPass;
    }

    public void setPerformPass(boolean performPass) {
        this.performPass = performPass;
    }
}
