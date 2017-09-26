package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/2.
 */

public class QueryBetBean {
    /**
     * status : null
     * errorMsg : null
     * data : {"queryid":"0","dataList":[{"serialNum":"200993","usaTime":"2016-12-30 03:03:40","chinaTime":"2016-12-3003: 03: 40","detailedInfo":"电子游戏自动反水","BetAmount":"0.15","keying":"40","fanshui ":"200","paichai ":"100","state":"成功"},{"serialNum":"200993","usaTime":"2016-12-30 03:03:40","chinaTime":"2016-12-3003: 03: 40","detailedInfo":"电子游戏自动反水","BetAmount":"0.15","keying":"40","fanshui ":"200","paichai ":"100","state":"成功"}]}
     */

    private Object status;
    private Object errorMsg;
    private DataBean data;

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
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
        /**
         * queryid : 0
         * dataList : [{"serialNum":"200993","usaTime":"2016-12-30 03:03:40","chinaTime":"2016-12-3003: 03: 40","detailedInfo":"电子游戏自动反水","BetAmount":"0.15","keying":"40","fanshui ":"200","paichai ":"100","state":"成功"},{"serialNum":"200993","usaTime":"2016-12-30 03:03:40","chinaTime":"2016-12-3003: 03: 40","detailedInfo":"电子游戏自动反水","BetAmount":"0.15","keying":"40","fanshui ":"200","paichai ":"100","state":"成功"}]
         */

        private String queryid;
        private List<DataListBean> dataList;

        public String getQueryid() {
            return queryid;
        }

        public void setQueryid(String queryid) {
            this.queryid = queryid;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * serialNum : 200993
             * usaTime : 2016-12-30 03:03:40
             * chinaTime : 2016-12-3003: 03: 40
             * detailedInfo : 电子游戏自动反水
             * BetAmount : 0.15
             * keying : 40
             * fanshui  : 200
             * paichai  : 100
             * state : 成功
             */

            private String serialNum;
            private String usaTime;
            private String chinaTime;
            private String detailedInfo;
            private String BetAmount;
            private String keying;
            private String fanshui;
            private String paichai;
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

            public String getDetailedInfo() {
                return detailedInfo;
            }

            public void setDetailedInfo(String detailedInfo) {
                this.detailedInfo = detailedInfo;
            }

            public String getBetAmount() {
                return BetAmount;
            }

            public void setBetAmount(String BetAmount) {
                this.BetAmount = BetAmount;
            }

            public String getKeying() {
                return keying;
            }

            public void setKeying(String keying) {
                this.keying = keying;
            }

            public String getFanshui() {
                return fanshui;
            }

            public void setFanshui(String fanshui) {
                this.fanshui = fanshui;
            }

            public String getPaichai() {
                return paichai;
            }

            public void setPaichai(String paichai) {
                this.paichai = paichai;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }
        }
    }
}
