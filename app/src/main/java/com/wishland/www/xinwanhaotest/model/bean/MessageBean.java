package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MessageBean {

    /**
     * status : 200
     * data : {"unReadMsg":1,"queryId":21664273,"dataList":[{"isNew":1,"msgId":21664273,"time":"2017-05-29 23:19:25","title":"【5月30日12:00，浓情端午抢粽子活动准时开始】","detailedInfo":"5月30日12:00，浓情端午抢粽子活动准时开始，30日当天在线支付赠送1.5%入款优惠，次次存次次送~千万别错过！","from":""}]}
     */

    private int status;
    private DataBean data;

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

    public static class DataBean {
        /**
         * unReadMsg : 1
         * queryId : 21664273
         * dataList : [{"isNew":1,"msgId":21664273,"time":"2017-05-29 23:19:25","title":"【5月30日12:00，浓情端午抢粽子活动准时开始】","detailedInfo":"5月30日12:00，浓情端午抢粽子活动准时开始，30日当天在线支付赠送1.5%入款优惠，次次存次次送~千万别错过！","from":""}]
         */

        private int unReadMsg;
        private int queryId;
        private List<DataListBean> dataList;

        public int getUnReadMsg() {
            return unReadMsg;
        }

        public void setUnReadMsg(int unReadMsg) {
            this.unReadMsg = unReadMsg;
        }

        public int getQueryId() {
            return queryId;
        }

        public void setQueryId(int queryId) {
            this.queryId = queryId;
        }

        public List<DataListBean> getDataList() {
            return dataList;
        }

        public void setDataList(List<DataListBean> dataList) {
            this.dataList = dataList;
        }

        public static class DataListBean {
            /**
             * isNew : 1
             * msgId : 21664273
             * time : 2017-05-29 23:19:25
             * title : 【5月30日12:00，浓情端午抢粽子活动准时开始】
             * detailedInfo : 5月30日12:00，浓情端午抢粽子活动准时开始，30日当天在线支付赠送1.5%入款优惠，次次存次次送~千万别错过！
             * from :
             */

            private int isNew;
            private int msgId;
            private String time;
            private String title;
            private String detailedInfo;
            private String from;

            public int getIsNew() {
                return isNew;
            }

            public void setIsNew(int isNew) {
                this.isNew = isNew;
            }

            public int getMsgId() {
                return msgId;
            }

            public void setMsgId(int msgId) {
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

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }
        }
    }
}
