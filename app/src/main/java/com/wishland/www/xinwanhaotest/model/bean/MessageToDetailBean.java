package com.wishland.www.xinwanhaotest.model.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MessageToDetailBean implements Serializable {

    /**
     * isNew : 0
     * msgId : 123
     * time : 2016-12-30 03:03:40
     * title : 主标题,祝广大会员平安夜...
     * detailedInfo : 电子游戏自动反水
     */

    private String msgId;
    private String time;
    private String title;
    private String detailedInfo;

    public MessageToDetailBean(String msgId, String time, String title, String detailedInfo) {
        this.msgId = msgId;
        this.time = time;
        this.title = title;
        this.detailedInfo = detailedInfo;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetailedInfo() {
        return detailedInfo;
    }

    public void setDetailedInfo(String detailedInfo) {
        this.detailedInfo = detailedInfo;
    }

    @Override
    public String toString() {
        return "MessageToDetailBean{" +
                "msgId='" + msgId + '\'' +
                ", time='" + time + '\'' +
                ", title='" + title + '\'' +
                ", detailedInfo='" + detailedInfo + '\'' +
                '}';
    }
}

