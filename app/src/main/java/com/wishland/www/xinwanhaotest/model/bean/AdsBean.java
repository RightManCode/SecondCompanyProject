package com.wishland.www.xinwanhaotest.model.bean;

/**
 * Created by Administrator on 2017/7/23.
 */

public class AdsBean {
    /**
     * status : 200
     * data : {"code":"zxxx","title":"欢迎莅临VIP万濠会，感受无与伦比线上博彩乐趣；VIP万濠会365天X24小时专业的客户服务，全天候处理相关事宜，严格训练的客服团队，以专业、亲切的态度，让每位玩家宾至如归的感觉！网银、支付宝、微信等等多元化充值方式，全新的充值方式，全新的服务理念，尽在VIP万濠会！各类丰富真人视讯、电子游艺、体育项目、彩票游戏，精彩无限！相信VIP万濠会，相信品牌的力量！"}
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
         * code : zxxx
         * title : 欢迎莅临VIP万濠会，感受无与伦比线上博彩乐趣；VIP万濠会365天X24小时专业的客户服务，全天候处理相关事宜，严格训练的客服团队，以专业、亲切的态度，让每位玩家宾至如归的感觉！网银、支付宝、微信等等多元化充值方式，全新的充值方式，全新的服务理念，尽在VIP万濠会！各类丰富真人视讯、电子游艺、体育项目、彩票游戏，精彩无限！相信VIP万濠会，相信品牌的力量！
         */

        private String code;
        private String title;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
