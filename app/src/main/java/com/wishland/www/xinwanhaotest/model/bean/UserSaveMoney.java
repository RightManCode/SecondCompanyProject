package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/6.
 */

public class UserSaveMoney {

    /**
     * status : 200
     * data : {"user":{"username":"testjishu","money":"699.420"},"select":[{"code":"WXZF","img":"http://029256.com/member/pay/skin/hxbank/wechatpay.png"},{"code":"ALIPAY","img":"http://029256.com/member/pay/skin/hxbank/alipay.png"}],"limit":10,"url":"http://029256.com/api.d/url_agent.php?token=[token]&url=pay&type=rhlepay&id=93&money=[money]&code=[code]"}
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
         * user : {"username":"testjishu","money":"699.420"}
         * select : [{"code":"WXZF","img":"http://029256.com/member/pay/skin/hxbank/wechatpay.png"},{"code":"ALIPAY","img":"http://029256.com/member/pay/skin/hxbank/alipay.png"}]
         * limit : 10
         * url : http://029256.com/api.d/url_agent.php?token=[token]&url=pay&type=rhlepay&id=93&money=[money]&code=[code]
         */

        private UserBean user;
        private int limit;
        private String url;
        private List<SelectBean> select;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public int getLimit() {
            return limit;
        }

        public void setLimit(int limit) {
            this.limit = limit;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<SelectBean> getSelect() {
            return select;
        }

        public void setSelect(List<SelectBean> select) {
            this.select = select;
        }

        public static class UserBean {
            /**
             * username : testjishu
             * money : 699.420
             */

            private String username;
            private String money;

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }
        }

        public static class SelectBean {
            /**
             * code : WXZF
             * img : http://029256.com/member/pay/skin/hxbank/wechatpay.png
             */

            private String code;
            private String img;

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }
}
