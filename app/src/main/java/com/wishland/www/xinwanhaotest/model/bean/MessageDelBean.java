package com.wishland.www.xinwanhaotest.model.bean;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MessageDelBean {
    /**
     * status : 1
     * errorMsg : null
     * data : {}
     */

    private int status;
    private Object errorMsg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
    }
}
