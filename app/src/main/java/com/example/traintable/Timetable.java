package com.example.traintable;

public class Timetable {

    private String timeStart;
    private String timeEnd;
    private String timeDuration;
    private String stationStart;
    private String stationEnd;
    private String price;

    public Timetable(String timeStart, String timeEnd, String timeDuration, String stationStart, String stationEnd, String price) {
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.timeDuration = timeDuration;
        this.stationStart = stationStart;
        this.stationEnd = stationEnd;
        this.price = price;
    }

    public String getTimeStart() {
        return this.timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return this.timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeDuration() {
        return this.timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public String getStationStart() {
        return this.stationStart;
    }

    public void setStationStart(String stationStart) {
        this.stationStart = stationStart;
    }

    public String getStationEnd() {
        return this.stationEnd;
    }

    public void setStationEnd(String stationEnd) {
        this.stationEnd = stationEnd;
    }

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}