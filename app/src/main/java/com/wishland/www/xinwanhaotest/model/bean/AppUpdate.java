package com.wishland.www.xinwanhaotest.model.bean;

import java.util.Map;

/**
 * Created by erha on 2017/8/23.
 */

public class AppUpdate {

    /**
     * success : true
     * message :
     * entity : {"versionType":3,"downloadUrl":{"app-release":"https://tpfw.083075.com:8080/tizi/static/upload/appversion/aicaipiao/shouji/android/gengxinbao/app-release.apk"},"version":"8"}
     */

    private boolean success;
    private String message;
    private EntityBean entity;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EntityBean getEntity() {
        return entity;
    }

    public void setEntity(EntityBean entity) {
        this.entity = entity;
    }

    public static class EntityBean {
        /**
         * versionType : 3
         * downloadUrl : {"app-release":"https://tpfw.083075.com:8080/tizi/static/upload/appversion/aicaipiao/shouji/android/gengxinbao/app-release.apk"}
         * version : 8
         */

        private int versionType;
        private Map<String, String> downloadUrl;
        private int version;

        public int getVersionType() {
            return versionType;
        }

        public void setVersionType(int versionType) {
            this.versionType = versionType;
        }

        public Map<String, String> getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(Map<String, String> downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }
}