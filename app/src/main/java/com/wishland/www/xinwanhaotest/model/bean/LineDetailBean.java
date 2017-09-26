package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class LineDetailBean {
    /**
     * status : 200
     * data : {"queryid":25,"list":[{"uid":68352,"usaTime":"2017-05-18 04:40:50","chinaTime":"2017-05-17 16:40:50","from":"wallet","to":"ag","serialNum":"2017051804405013392","sum":100,"desc":"已处理","status":1,"result":"转账成功"},{"uid":68352,"usaTime":"2017-05-13 02:39:51","chinaTime":"2017-05-12 14:39:51","from":"wallet","to":"ag","serialNum":"2017051302395130761","sum":10,"desc":"已处理","status":1,"result":"转账成功"}]}
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
         * queryid : 25
         * list : [{"uid":68352,"usaTime":"2017-05-18 04:40:50","chinaTime":"2017-05-17 16:40:50","from":"wallet","to":"ag","serialNum":"2017051804405013392","sum":100,"desc":"已处理","status":1,"result":"转账成功"},{"uid":68352,"usaTime":"2017-05-13 02:39:51","chinaTime":"2017-05-12 14:39:51","from":"wallet","to":"ag","serialNum":"2017051302395130761","sum":10,"desc":"已处理","status":1,"result":"转账成功"}]
         */

        private int queryid;
        private List<ListBean> list;

        public int getQueryid() {
            return queryid;
        }

        public void setQueryid(int queryid) {
            this.queryid = queryid;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * uid : 68352
             * usaTime : 2017-05-18 04:40:50
             * chinaTime : 2017-05-17 16:40:50
             * from : wallet
             * to : ag
             * serialNum : 2017051804405013392
             * sum : 100
             * desc : 已处理
             * status : 1
             * result : 转账成功
             */

            private int uid;
            private String usaTime;
            private String chinaTime;
            private String from;
            private String to;
            private String serialNum;
            private int sum;
            private String desc;
            private int status;
            private String result;

            public int getUid() {
                return uid;
            }

            public void setUid(int uid) {
                this.uid = uid;
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

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public String getSerialNum() {
                return serialNum;
            }

            public void setSerialNum(String serialNum) {
                this.serialNum = serialNum;
            }

            public int getSum() {
                return sum;
            }

            public void setSum(int sum) {
                this.sum = sum;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getResult() {
                return result;
            }

            public void setResult(String result) {
                this.result = result;
            }
        }
    }
}
