package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/23.
 */

public class ABean {
    /**
     * status : 200
     * data : [{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img023.jpg","title":"V5会员请进入PC版领取礼品","start":"7月25日至31日","end":"","url":"http://029256.com/m/activity.php?id=0"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img01.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=1"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img02.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=2"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img03.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=3"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img04.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=4"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img05.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=5"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img06.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=6"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img07.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=7"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img08.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=8"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img09.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=9"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img010.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=10"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img011.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=11"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img012.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=12"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img013.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=13"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img014.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=14"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img015.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=15"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img016.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=16"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img017.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=17"},{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img018.png","title":"活动对象：万濠会所有新老会员","start":"全年度活动 长期有效","end":"","url":"http://029256.com/m/activity.php?id=18"}]
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
         * imgUrl : http://029256.com/m/data/templates/v.3/images/activity/hd_img023.jpg
         * title : V5会员请进入PC版领取礼品
         * start : 7月25日至31日
         * end :
         * url : http://029256.com/m/activity.php?id=0
         */

        private String imgUrl;
        private String title;
        private String start;
        private String end;
        private String url;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
