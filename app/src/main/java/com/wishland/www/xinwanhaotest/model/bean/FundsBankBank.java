package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/31.
 */

public class FundsBankBank {

    /**
     * status : 200
     * data : {"pay_list":[{"title":"01 在线支付","title_img":"http://029256.com/m/data/templates/v.3/images/info_icon01.png","item":[{"name":"五：输入手机号码短信支付送1%","para":"type=jfpay&id=101"},{"name":"八：微信QQ钱包充值1%","para":"type=zespay_scan&id=119"}],"type":"pay"},{"title":"02 公司入款","title_img":"http://029256.com/m/data/templates/v.3/images/info_icon02.png","item":[{"name":"汇款提交","para":"type=hk"}],"type":"hk"},{"title":"03 支付宝入款","title_img":"http://029256.com/m/data/templates/v.3/images/info_icon02.png","item":[{"name":"支付宝提交","para":"type=alipay"}],"type":"alipay"}],"url":"http://029256.com/api/index.php?pay/para&[para]","data_img":"http://029256.com/m/data/templates/v.3/images/info_icon03.png"}
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
         * pay_list : [{"title":"01 在线支付","title_img":"http://029256.com/m/data/templates/v.3/images/info_icon01.png","item":[{"name":"五：输入手机号码短信支付送1%","para":"type=jfpay&id=101"},{"name":"八：微信QQ钱包充值1%","para":"type=zespay_scan&id=119"}],"type":"pay"},{"title":"02 公司入款","title_img":"http://029256.com/m/data/templates/v.3/images/info_icon02.png","item":[{"name":"汇款提交","para":"type=hk"}],"type":"hk"},{"title":"03 支付宝入款","title_img":"http://029256.com/m/data/templates/v.3/images/info_icon02.png","item":[{"name":"支付宝提交","para":"type=alipay"}],"type":"alipay"}]
         * url : http://029256.com/api/index.php?pay/para&[para]
         * data_img : http://029256.com/m/data/templates/v.3/images/info_icon03.png
         */

        private String url;
        private String data_img;
        private List<PayListBean> pay_list;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getData_img() {
            return data_img;
        }

        public void setData_img(String data_img) {
            this.data_img = data_img;
        }

        public List<PayListBean> getPay_list() {
            return pay_list;
        }

        public void setPay_list(List<PayListBean> pay_list) {
            this.pay_list = pay_list;
        }

        public static class PayListBean {
            /**
             * title : 01 在线支付
             * title_img : http://029256.com/m/data/templates/v.3/images/info_icon01.png
             * item : [{"name":"五：输入手机号码短信支付送1%","para":"type=jfpay&id=101"},{"name":"八：微信QQ钱包充值1%","para":"type=zespay_scan&id=119"}]
             * type : pay
             */

            private String title;
            private String title_img;
            private String type;
            private List<ItemBean> item;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTitle_img() {
                return title_img;
            }

            public void setTitle_img(String title_img) {
                this.title_img = title_img;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<ItemBean> getItem() {
                return item;
            }

            public void setItem(List<ItemBean> item) {
                this.item = item;
            }

            public static class ItemBean {
                /**
                 * name : 五：输入手机号码短信支付送1%
                 * para : type=jfpay&id=101
                 */

                private String name;
                private String para;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getPara() {
                    return para;
                }

                public void setPara(String para) {
                    this.para = para;
                }
            }
        }
    }
}
