package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/18.
 */

public class AccountBean{


    /**
     * status : 200
     * data : {"accountInfo":{"accountName":"test5588","registerTime":"2016-02-24 02:25:15","lastLoginTime":"2017-07-24 02:52:58"},"balanceInfo":{"balance":"4.260","drawingBet":1316742},"financeInfo":{"PayName":"测试账号","Bank":"中信银行","BankAccount":"000","AccountAddress":""},"remittanceInfo":{"remittanceType":[{"typeID":"银行柜台","title":"银行柜台"},{"typeID":"ATM现金","title":"ATM现金"},{"typeID":"ATM卡转","title":"ATM卡转"},{"typeID":"网银转账","title":"网银转账"},{"typeID":0,"title":"其它[手动输入]"}],"companyAccount":[{"typeID":"包商银行【郑专】","title":"包商银行【郑专】"}]}}
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
         * accountInfo : {"accountName":"test5588","registerTime":"2016-02-24 02:25:15","lastLoginTime":"2017-07-24 02:52:58"}
         * balanceInfo : {"balance":"4.260","drawingBet":1316742}
         * financeInfo : {"PayName":"测试账号","Bank":"中信银行","BankAccount":"000","AccountAddress":""}
         * remittanceInfo : {"remittanceType":[{"typeID":"银行柜台","title":"银行柜台"},{"typeID":"ATM现金","title":"ATM现金"},{"typeID":"ATM卡转","title":"ATM卡转"},{"typeID":"网银转账","title":"网银转账"},{"typeID":0,"title":"其它[手动输入]"}],"companyAccount":[{"typeID":"包商银行【郑专】","title":"包商银行【郑专】"}]}
         */

        private AccountInfoBean accountInfo;
        private BalanceInfoBean balanceInfo;
        private FinanceInfoBean financeInfo;
        private RemittanceInfoBean remittanceInfo;

        public AccountInfoBean getAccountInfo() {
            return accountInfo;
        }

        public void setAccountInfo(AccountInfoBean accountInfo) {
            this.accountInfo = accountInfo;
        }

        public BalanceInfoBean getBalanceInfo() {
            return balanceInfo;
        }

        public void setBalanceInfo(BalanceInfoBean balanceInfo) {
            this.balanceInfo = balanceInfo;
        }

        public FinanceInfoBean getFinanceInfo() {
            return financeInfo;
        }

        public void setFinanceInfo(FinanceInfoBean financeInfo) {
            this.financeInfo = financeInfo;
        }

        public RemittanceInfoBean getRemittanceInfo() {
            return remittanceInfo;
        }

        public void setRemittanceInfo(RemittanceInfoBean remittanceInfo) {
            this.remittanceInfo = remittanceInfo;
        }

        public static class AccountInfoBean {
            /**
             * accountName : test5588
             * registerTime : 2016-02-24 02:25:15
             * lastLoginTime : 2017-07-24 02:52:58
             */

            private String accountName;
            private String registerTime;
            private String lastLoginTime;

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getRegisterTime() {
                return registerTime;
            }

            public void setRegisterTime(String registerTime) {
                this.registerTime = registerTime;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }
        }

        public static class BalanceInfoBean {
            /**
             * balance : 4.260
             * drawingBet : 1316742
             */

            private String balance;
            private int drawingBet;

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public int getDrawingBet() {
                return drawingBet;
            }

            public void setDrawingBet(int drawingBet) {
                this.drawingBet = drawingBet;
            }
        }

        public static class FinanceInfoBean {
            /**
             * PayName : 测试账号
             * Bank : 中信银行
             * BankAccount : 000
             * AccountAddress :
             */

            private String PayName;
            private String Bank;
            private String BankAccount;
            private String AccountAddress;

            public String getPayName() {
                return PayName;
            }

            public void setPayName(String PayName) {
                this.PayName = PayName;
            }

            public String getBank() {
                return Bank;
            }

            public void setBank(String Bank) {
                this.Bank = Bank;
            }

            public String getBankAccount() {
                return BankAccount;
            }

            public void setBankAccount(String BankAccount) {
                this.BankAccount = BankAccount;
            }

            public String getAccountAddress() {
                return AccountAddress;
            }

            public void setAccountAddress(String AccountAddress) {
                this.AccountAddress = AccountAddress;
            }
        }

        public static class RemittanceInfoBean {
            private List<RemittanceTypeBean> remittanceType;
            private List<CompanyAccountBean> companyAccount;

            public List<RemittanceTypeBean> getRemittanceType() {
                return remittanceType;
            }

            public void setRemittanceType(List<RemittanceTypeBean> remittanceType) {
                this.remittanceType = remittanceType;
            }

            public List<CompanyAccountBean> getCompanyAccount() {
                return companyAccount;
            }

            public void setCompanyAccount(List<CompanyAccountBean> companyAccount) {
                this.companyAccount = companyAccount;
            }

            public static class RemittanceTypeBean {
                /**
                 * typeID : 银行柜台
                 * title : 银行柜台
                 */

                private String typeID;
                private String title;

                public String getTypeID() {
                    return typeID;
                }

                public void setTypeID(String typeID) {
                    this.typeID = typeID;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }

            public static class CompanyAccountBean {
                /**
                 * typeID : 包商银行【郑专】
                 * title : 包商银行【郑专】
                 */

                private String typeID;
                private String title;

                public String getTypeID() {
                    return typeID;
                }

                public void setTypeID(String typeID) {
                    this.typeID = typeID;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }
            }
        }
    }
}
