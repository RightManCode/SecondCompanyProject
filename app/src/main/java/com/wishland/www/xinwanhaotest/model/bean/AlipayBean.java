package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class AlipayBean {
    /**
     * status : 200
     * data : {"limit":"10","tip1":"01 请选择以下公司账号进行转账汇款","tip2":"02 填写汇款单","tip":["1、请每次支付宝入款前，查看转账提交页面的最新入款账号，以免误入已停用帐号而造成您资金损失，如存入过期账户，我司则无法受理您的业务。","2、在资金转出之后请务必填写该页下方的转账信息表格，并且备注上转账人姓名，转账方式以便财务系统能够及时的为您确认并添加金额到您的会员账户中。"],"account":[{"picName":"张美月","alipayName":"【张美月】支付宝转账招商银行卡立即到账","alipayID":"6214835956509408","memo":"请认真核对转账对象、名称。"}]}
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
         * limit : 10
         * tip1 : 01 请选择以下公司账号进行转账汇款
         * tip2 : 02 填写汇款单
         * tip : ["1、请每次支付宝入款前，查看转账提交页面的最新入款账号，以免误入已停用帐号而造成您资金损失，如存入过期账户，我司则无法受理您的业务。","2、在资金转出之后请务必填写该页下方的转账信息表格，并且备注上转账人姓名，转账方式以便财务系统能够及时的为您确认并添加金额到您的会员账户中。"]
         * account : [{"picName":"张美月","alipayName":"【张美月】支付宝转账招商银行卡立即到账","alipayID":"6214835956509408","memo":"请认真核对转账对象、名称。"}]
         */

        private String limit;
        private String tip1;
        private String tip2;
        private List<String> tip;
        private List<AccountBean> account;

        public String getLimit() {
            return limit;
        }

        public void setLimit(String limit) {
            this.limit = limit;
        }

        public String getTip1() {
            return tip1;
        }

        public void setTip1(String tip1) {
            this.tip1 = tip1;
        }

        public String getTip2() {
            return tip2;
        }

        public void setTip2(String tip2) {
            this.tip2 = tip2;
        }

        public List<String> getTip() {
            return tip;
        }

        public void setTip(List<String> tip) {
            this.tip = tip;
        }


        public List<AccountBean> getAccount() {
            return account;
        }

        public void setAccount(List<AccountBean> account) {
            this.account = account;
        }

        public static class AccountBean {
            /**
             * picName : 张美月
             * alipayName : 【张美月】支付宝转账招商银行卡立即到账
             * alipayID : 6214835956509408
             * memo : 请认真核对转账对象、名称。
             */

            private String picName;
            private String alipayName;
            private String alipayID;
            private String memo;
            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
            public String getPicName() {
                return picName;
            }

            public void setPicName(String picName) {
                this.picName = picName;
            }

            public String getAlipayName() {
                return alipayName;
            }

            public void setAlipayName(String alipayName) {
                this.alipayName = alipayName;
            }

            public String getAlipayID() {
                return alipayID;
            }

            public void setAlipayID(String alipayID) {
                this.alipayID = alipayID;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }
        }
    }
}
