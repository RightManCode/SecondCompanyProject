package com.wishland.www.xinwanhaotest.model.bean;

/**
 * Created by Administrator on 2017/4/19.
 */

public class LoginBean {


    /**
     * status : 200
     * data : {"status":"success"}
     * token : m8u5b65572Z1q2R652U3k7L6C3k7H7Q2E5V5v3B835c1e708h127e2c69334c799j2s9c7v290f1n6d329g9v3e569v6
     */

    private int status;
    private DataBean data;
    private String token;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "status=" + status +
                ", data=" + data +
                ", token='" + token + '\'' +
                '}';
    }

    public static class DataBean {
        /**
         * status : success
         */

        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "status='" + status + '\'' +
                    '}';
        }
    }
}


