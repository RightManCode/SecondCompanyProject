package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/26.
 */

public class HomeMoreBean {
    /**
     * status : 200
     * data : {"activity":[{"imgUrl":"http://029256.com/m/data/templates/v.3/images/activity/hd_img023.jpg","title":"V3会员请进入PC版本领取礼品","start":"V3会员请进入PC版本领取礼品","end":"","url":"http://029256.com/m/activity.php?id=0"}]}
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
        return "HomeMoreBean{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }

    public static class DataBean {
        private List<ActivityBean> activity;

        public List<ActivityBean> getActivity() {
            return activity;
        }

        public void setActivity(List<ActivityBean> activity) {
            this.activity = activity;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "activity=" + activity +
                    '}';
        }

        public static class ActivityBean {
            /**
             * imgUrl : http://029256.com/m/data/templates/v.3/images/activity/hd_img023.jpg
             * title : V3会员请进入PC版本领取礼品
             * start : V3会员请进入PC版本领取礼品
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

            @Override
            public String toString() {
                return "ActivityBean{" +
                        "imgUrl='" + imgUrl + '\'' +
                        ", title='" + title + '\'' +
                        ", start='" + start + '\'' +
                        ", end='" + end + '\'' +
                        ", url='" + url + '\'' +
                        '}';
            }
        }
    }
}
