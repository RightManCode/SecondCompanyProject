package com.wishland.www.xinwanhaotest.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/28.
 */

public class LineMessage implements Serializable {
    private String startyear;
    private String endyear;
    private String starthour;
    private String endhour;
    private String startmin;
    private String endmin;


    public LineMessage(String startyear, String endyear, String starthour, String endhour, String startmin, String endmin) {
        this.startyear = startyear;
        this.endyear = endyear;
        this.starthour = starthour;
        this.endhour = endhour;
        this.startmin = startmin;
        this.endmin = endmin;
    }

    public String getStartyear() {
        return startyear;
    }

    public void setStartyear(String startyear) {
        this.startyear = startyear;
    }

    public String getEndyear() {
        return endyear;
    }

    public void setEndyear(String endyear) {
        this.endyear = endyear;
    }

    public String getStarthour() {
        return starthour;
    }

    public void setStarthour(String starthour) {
        this.starthour = starthour;
    }

    public String getEndhour() {
        return endhour;
    }

    public void setEndhour(String endhour) {
        this.endhour = endhour;
    }

    public String getStartmin() {
        return startmin;
    }

    public void setStartmin(String startmin) {
        this.startmin = startmin;
    }

    public String getEndmin() {
        return endmin;
    }

    public void setEndmin(String endmin) {
        this.endmin = endmin;
    }

    @Override
    public String toString() {
        return "LineMessage{" +
                "startyear='" + startyear + '\'' +
                ", endyear='" + endyear + '\'' +
                ", starthour='" + starthour + '\'' +
                ", endhour='" + endhour + '\'' +
                ", startmin='" + startmin + '\'' +
                ", endmin='" + endmin + '\'' +
                '}';
    }
}
