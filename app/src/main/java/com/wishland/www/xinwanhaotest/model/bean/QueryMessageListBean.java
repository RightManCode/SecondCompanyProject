package com.wishland.www.xinwanhaotest.model.bean;

/**
 * Created by Administrator on 2017/5/9.
 */

public class QueryMessageListBean{
    /**
     * serialNum : 200993
     * usaTime : 2016-12-30 03:03:40
     * chinaTime : 2016-12-30 03:03:40
     * sum : 0.15
     * type : 反水派送
     * notes : 电子游戏自动反水
     * state : 成功
     */

    private String type;
    private String notes;

    /**
     * serialNum : 200993
     * usaTime : 2016-12-30 03:03:40
     * chinaTime : 2016-12-30 03:03:40
     * sum : 10.00
     * points : 0.10
     * state : 成功
     */

    private String serialNum;
    private String usaTime;
    private String chinaTime;
    private String sum;
    private String points;
    private String state;

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getUsaTime() {
        return usaTime;
    }

    public void setUsaTime(String usaTime) {
        this.usaTime = usaTime;
    }

    public String getChinaTime() {
        return chinaTime;
    }

    public void setChinaTime(String chinaTime) {
        this.chinaTime = chinaTime;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }


    @Override
    public String toString() {
        return "QueryMessageListBean{" +
                "type='" + type + '\'' +
                ", notes='" + notes + '\'' +
                ", serialNum='" + serialNum + '\'' +
                ", usaTime='" + usaTime + '\'' +
                ", chinaTime='" + chinaTime + '\'' +
                ", sum='" + sum + '\'' +
                ", points='" + points + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
