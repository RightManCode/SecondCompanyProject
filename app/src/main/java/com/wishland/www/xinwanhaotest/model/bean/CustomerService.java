package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by erha on 2017/8/23.
 */

public class CustomerService {

    /**
     * status : 200
     * data : [{"code":"service","content":"http://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=399959&configID=1784&jid=2462264492"},{"code":"service_gw","content":"http://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=399959&configID=1784&jid=2462264492"}]
     */

    private int status;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : service
         * content : http://v88.live800.com/live800/chatClient/chatbox.jsp?companyID=399959&configID=1784&jid=2462264492
         */

        private String code;
        private String content;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
