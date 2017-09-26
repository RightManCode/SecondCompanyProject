package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/25.
 */

public class AccountBankBean {
    /**
     * status : 200
     * data : [{"img":"http://029256.com/images/banklogo/1-中国农业银行.gif","bankno":"1","bankname":"中国农业银行"},{"img":"http://029256.com/images/banklogo/10-中信银行.gif","bankno":"10","bankname":"中信银行"},{"img":"http://029256.com/images/banklogo/2-中国银行.gif","bankno":"2","bankname":"中国银行"},{"img":"http://029256.com/images/banklogo/3-交通银行.gif","bankno":"3","bankname":"交通银行"},{"img":"http://029256.com/images/banklogo/4-中国建设银行.gif","bankno":"4","bankname":"中国建设银行"},{"img":"http://029256.com/images/banklogo/5-中国工商银行.gif","bankno":"5","bankname":"中国工商银行"},{"img":"http://029256.com/images/banklogo/6-中国邮政储蓄银行.gif","bankno":"6","bankname":"中国邮政储蓄银行"},{"img":"http://029256.com/images/banklogo/7-招商银行.gif","bankno":"7","bankname":"招商银行"},{"img":"http://029256.com/images/banklogo/8-上海浦东发展银行.gif","bankno":"8","bankname":"上海浦东发展银行"},{"img":"http://029256.com/images/banklogo/9-中国光大银行.gif","bankno":"9","bankname":"中国光大银行"},{"img":"http://029256.com/images/banklogo/11-平安银行.gif","bankno":"11","bankname":"平安银行"},{"img":"http://029256.com/images/banklogo/12-中国民生银行.gif","bankno":"12","bankname":"中国民生银行"},{"img":"http://029256.com/images/banklogo/13-华夏银行.gif","bankno":"13","bankname":"华夏银行"},{"img":"http://029256.com/images/banklogo/14-广东发展银行.gif","bankno":"14","bankname":"广东发展银行"},{"img":"http://029256.com/images/banklogo/15-兴业银行.gif","bankno":"15","bankname":"兴业银行"},{"img":"http://029256.com/images/banklogo/16-徽商银行.gif","bankno":"16","bankname":"徽商银行"},{"img":"http://029256.com/images/banklogo/17-长沙银行.gif","bankno":"17","bankname":"长沙银行"},{"img":"http://029256.com/images/banklogo/18-浙江省农村信用社联合社.gif","bankno":"18","bankname":"浙江省农村信用社联合社"}]
     * token : G0l5R9N7W5X0a2n1R3S2j293M5d464U6D453b1f57581e3p805b548b6m919s1q13431d5a9u8k7d8k5u0g321g70579
     */

    private int status;
    private String token;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * img : http://029256.com/images/banklogo/1-中国农业银行.gif
         * bankno : 1
         * bankname : 中国农业银行
         */

        private String img;
        private String bankno;
        private String bankname;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getBankno() {
            return bankno;
        }

        public void setBankno(String bankno) {
            this.bankno = bankno;
        }

        public String getBankname() {
            return bankname;
        }

        public void setBankname(String bankname) {
            this.bankname = bankname;
        }
    }
}
