package model;

import java.sql.Time;

public class Feeding {

    private int id;              // om du har en primärnyckel i din tabell
    private String horseName;    // namnet på hästen
    private String feedType;     // typ av foder
    private double amount;       // mängd i kg
    private Time time;           // tidpunkt
    private String comments;     // kommentarer

    public Feeding() {
    }

    public Feeding(int id, String horseName, String feedType, double amount, Time time, String comments) {
        this.id = id;
        this.horseName = horseName;
        this.feedType = feedType;
        this.amount = amount;
        this.time = time;
        this.comments = comments;
    }

    // Om du inte vill använda id i konstruktorn
    public Feeding(String horseName, String feedType, double amount, Time time, String comments) {
        
    	this.horseName = horseName;
        this.feedType = feedType;
        this.amount = amount;
        this.time = time;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public String getFeedType() {
        return feedType;
    }

    public void setFeedType(String feedType) {
        this.feedType = feedType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "Feeding{" +
                "id=" + id +
                ", horseName='" + horseName + '\'' +
                ", feedType='" + feedType + '\'' +
                ", amount=" + amount +
                ", time=" + time +
                ", comments='" + comments + '\'' +
                '}';
    }
}
