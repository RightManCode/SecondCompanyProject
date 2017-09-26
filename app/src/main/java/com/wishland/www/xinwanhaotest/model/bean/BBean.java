package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/23.
 */

public class BBean {

    /**
     * status : 200
     * data : [{"title":"","img":"http://029256.com/m/./data/templates/v.3/images/banner/10.jpg","url":""},{"title":"","img":"http://029256.com/m/./data/templates/v.3/images/banner/17.jpg","url":"http://029256.com/api/url_agent.php?token=[token]&url=http%3A%2F%2Fwww.sjb118.com%2F"},{"title":"","img":"http://029256.com/m/./data/templates/v.3/images/banner/7.jpg","url":""},{"title":"","img":"http://029256.com/m/./data/templates/v.3/images/banner/16.jpg","url":"http://029256.com/api/url_agent.php?token=[token]&url=http%3A%2F%2Fqhb.whh89.com"},{"title":"","img":"http://029256.com/m/./data/templates/v.3/images/banner/6.jpg","url":"http://029256.com/api/url_agent.php?token=[token]&url=http%3A%2F%2Fjc.whh1133.com%2F"}]
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
         * title :
         * img : http://029256.com/m/./data/templates/v.3/images/banner/10.jpg
         * url :
         */

        private String title;
        private String img;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
