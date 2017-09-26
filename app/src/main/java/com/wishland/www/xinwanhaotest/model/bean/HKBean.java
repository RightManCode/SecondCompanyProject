package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class HKBean {
    /**
     * status : 200
     * data : {"limit":"10","tip1":"01 请选择以下公司账号进行转账汇款","tip2":"02 填写汇款单","tip":["一、在金额转出之后请务必填写该页下方的汇款信息表格，以便财务系统能够及时的为您确认并添加金额到您的会员帐户中。","二、本公司最低存款金额为10元，公司财务系统将对银行存款的会员按实际存款金额实行返利派送。","三、跨行转帐请您使用跨行快汇。"],"type":["银行柜台","ATM现金","ATM卡转","网银转账","其它[手动输入]"],"bank":[{"bank":"包商银行【郑专】","cardid":"50000000000051711720","name":"郑专","address":"包商银行乌兰察布分行广场支行"}]}
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
         * tip : ["一、在金额转出之后请务必填写该页下方的汇款信息表格，以便财务系统能够及时的为您确认并添加金额到您的会员帐户中。","二、本公司最低存款金额为10元，公司财务系统将对银行存款的会员按实际存款金额实行返利派送。","三、跨行转帐请您使用跨行快汇。"]
         * type : ["银行柜台","ATM现金","ATM卡转","网银转账","其它[手动输入]"]
         * bank : [{"bank":"包商银行【郑专】","cardid":"50000000000051711720","name":"郑专","address":"包商银行乌兰察布分行广场支行"}]
         */

        private String limit;
        private String tip1;
        private String tip2;
        private List<String> tip;
        private List<String> type;
        private List<BankBean> bank;

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

        public List<String> getType() {
            return type;
        }

        public void setType(List<String> type) {
            this.type = type;
        }

        public List<BankBean> getBank() {
            return bank;
        }

        public void setBank(List<BankBean> bank) {
            this.bank = bank;
        }

        public static class BankBean {
            /**
             * bank : 包商银行【郑专】
             * cardid : 50000000000051711720
             * name : 郑专
             * address : 包商银行乌兰察布分行广场支行
             */

            private String bank;
            private String cardid;
            private String name;
            private String address;

            public String getBank() {
                return bank;
            }

            public void setBank(String bank) {
                this.bank = bank;
            }

            public String getCardid() {
                return cardid;
            }

            public void setCardid(String cardid) {
                this.cardid = cardid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }
    }
}
