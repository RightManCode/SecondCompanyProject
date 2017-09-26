package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class GlobalBean {
    /**
     * status : 200
     * data : {"BetQueryType":{"ty":[{"id":1,"name":"体育单式"},{"id":2,"name":"体育串关"}],"ss":[{"id":1,"name":"重庆时时彩"},{"id":2,"name":"广东快乐十分"},{"id":3,"name":"北京赛车PK拾"},{"id":11,"name":"江西时时彩"},{"id":12,"name":"天津时时彩"},{"id":13,"name":"广西十分彩"},{"id":14,"name":"广东11选5"},{"id":21,"name":"重庆快乐十分"},{"id":22,"name":"天津快乐十分"}],"pt":[{"id":1,"name":"北京快乐8"},{"id":2,"name":"上海时时乐"},{"id":3,"name":"福彩3D"},{"id":4,"name":"排列三"}]},"bankList":[{"bankName":"农业银行","bankCode":"1"},{"bankName":"中信银行","bankCode":"10"},{"bankName":"平安银行","bankCode":"11"},{"bankName":"民生银行","bankCode":"12"},{"bankName":"华夏银行","bankCode":"13"},{"bankName":"广发银行","bankCode":"14"},{"bankName":"兴业银行","bankCode":"15"},{"bankName":"徽商银行","bankCode":"16"},{"bankName":"长沙银行","bankCode":"17"},{"bankName":"浙江农信","bankCode":"18"},{"bankName":"中国银行","bankCode":"2"},{"bankName":"交通银行","bankCode":"3"},{"bankName":"建设银行","bankCode":"4"},{"bankName":"工商银行","bankCode":"5"},{"bankName":"邮储银行","bankCode":"6"},{"bankName":"招商银行","bankCode":"7"},{"bankName":"浦发银行","bankCode":"8"},{"bankName":"光大银行","bankCode":"9"}],"tradeSubType":[{"id":"all","name":"全部"},{"id":"cjps","name":"彩金派送"},{"id":"fsps","name":"反水派送"},{"id":"rghk","name":"人工汇款"},{"id":"other","name":"其他"}]}
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

    @Override
    public String toString() {
        return "GlobalBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        /**
         * BetQueryType : {"ty":[{"id":1,"name":"体育单式"},{"id":2,"name":"体育串关"}],"ss":[{"id":1,"name":"重庆时时彩"},{"id":2,"name":"广东快乐十分"},{"id":3,"name":"北京赛车PK拾"},{"id":11,"name":"江西时时彩"},{"id":12,"name":"天津时时彩"},{"id":13,"name":"广西十分彩"},{"id":14,"name":"广东11选5"},{"id":21,"name":"重庆快乐十分"},{"id":22,"name":"天津快乐十分"}],"pt":[{"id":1,"name":"北京快乐8"},{"id":2,"name":"上海时时乐"},{"id":3,"name":"福彩3D"},{"id":4,"name":"排列三"}]}
         * bankList : [{"bankName":"农业银行","bankCode":"1"},{"bankName":"中信银行","bankCode":"10"},{"bankName":"平安银行","bankCode":"11"},{"bankName":"民生银行","bankCode":"12"},{"bankName":"华夏银行","bankCode":"13"},{"bankName":"广发银行","bankCode":"14"},{"bankName":"兴业银行","bankCode":"15"},{"bankName":"徽商银行","bankCode":"16"},{"bankName":"长沙银行","bankCode":"17"},{"bankName":"浙江农信","bankCode":"18"},{"bankName":"中国银行","bankCode":"2"},{"bankName":"交通银行","bankCode":"3"},{"bankName":"建设银行","bankCode":"4"},{"bankName":"工商银行","bankCode":"5"},{"bankName":"邮储银行","bankCode":"6"},{"bankName":"招商银行","bankCode":"7"},{"bankName":"浦发银行","bankCode":"8"},{"bankName":"光大银行","bankCode":"9"}]
         * tradeSubType : [{"id":"all","name":"全部"},{"id":"cjps","name":"彩金派送"},{"id":"fsps","name":"反水派送"},{"id":"rghk","name":"人工汇款"},{"id":"other","name":"其他"}]
         */

        private BetQueryTypeBean BetQueryType;
        private List<BankListBean> bankList;
        private List<TradeSubTypeBean> tradeSubType;

        public BetQueryTypeBean getBetQueryType() {
            return BetQueryType;
        }

        public void setBetQueryType(BetQueryTypeBean BetQueryType) {
            this.BetQueryType = BetQueryType;
        }

        public List<BankListBean> getBankList() {
            return bankList;
        }

        public void setBankList(List<BankListBean> bankList) {
            this.bankList = bankList;
        }

        public List<TradeSubTypeBean> getTradeSubType() {
            return tradeSubType;
        }

        public void setTradeSubType(List<TradeSubTypeBean> tradeSubType) {
            this.tradeSubType = tradeSubType;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "BetQueryType=" + BetQueryType +
                    ", bankList=" + bankList +
                    ", tradeSubType=" + tradeSubType +
                    '}';
        }

        public static class BetQueryTypeBean {
            private List<TyBean> ty;
            private List<SsBean> ss;
            private List<PtBean> pt;

            public List<TyBean> getTy() {
                return ty;
            }

            public void setTy(List<TyBean> ty) {
                this.ty = ty;
            }

            public List<SsBean> getSs() {
                return ss;
            }

            public void setSs(List<SsBean> ss) {
                this.ss = ss;
            }

            public List<PtBean> getPt() {
                return pt;
            }

            public void setPt(List<PtBean> pt) {
                this.pt = pt;
            }

            @Override
            public String toString() {
                return "BetQueryTypeBean{" +
                        "ty=" + ty +
                        ", ss=" + ss +
                        ", pt=" + pt +
                        '}';
            }

            public static class TyBean {
                /**
                 * id : 1
                 * name : 体育单式
                 */

                private int id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String toString() {
                    return "TyBean{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            '}';
                }
            }

            public static class SsBean {
                /**
                 * id : 1
                 * name : 重庆时时彩
                 */

                private int id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String toString() {
                    return "SsBean{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            '}';
                }
            }

            public static class PtBean {
                /**
                 * id : 1
                 * name : 北京快乐8
                 */

                private int id;
                private String name;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                @Override
                public String toString() {
                    return "PtBean{" +
                            "id=" + id +
                            ", name='" + name + '\'' +
                            '}';
                }
            }
        }

        public static class BankListBean {
            /**
             * bankName : 农业银行
             * bankCode : 1
             */

            private String bankName;
            private String bankCode;

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String bankName) {
                this.bankName = bankName;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String bankCode) {
                this.bankCode = bankCode;
            }

            @Override
            public String toString() {
                return "BankListBean{" +
                        "bankName='" + bankName + '\'' +
                        ", bankCode='" + bankCode + '\'' +
                        '}';
            }
        }

        public static class TradeSubTypeBean {
            /**
             * id : all
             * name : 全部
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String toString() {
                return "TradeSubTypeBean{" +
                        "id='" + id + '\'' +
                        ", name='" + name + '\'' +
                        '}';
            }
        }
    }
}
