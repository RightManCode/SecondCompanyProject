package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/30.
 */

public class LineMoneyBean {

    /**
     * status : null
     * errorMsg : null
     * data : {"wallet":[{"name":"MG电子游戏","amout":"1800","wallettype":"123"}]}
     */

    private int status;
    private String errorMsg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<WalletBean> wallet;

        public List<WalletBean> getWallet() {
            return wallet;
        }

        public void setWallet(List<WalletBean> wallet) {
            this.wallet = wallet;
        }

        public static class WalletBean {
            /**
             * name : MG电子游戏
             * amout : 1800
             * wallettype : 123
             */

            private String name;
            private String amout;
            private String wallettype;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAmout() {
                return amout;
            }

            public void setAmout(String amout) {
                this.amout = amout;
            }

            public String getWallettype() {
                return wallettype;
            }

            public void setWallettype(String wallettype) {
                this.wallettype = wallettype;
            }
        }
    }
}
