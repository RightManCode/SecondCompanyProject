package com.wishland.www.xinwanhaotest.model.bean;

import java.util.List;

/**
 * Created by admin on 2017/9/9.
 */

public class DomainBean {

    /**
     * stat : 0
     * data : [{"domainState":"0","operatorUser":"SystemOperator","remark":"","removed":0,"systemCode":"xpj","createTime":1505111973000,"domain":"https://www.pj78777.com","lastModifyTime":1505112284000,"createUser":"SystemCreate","id":20},{"domainState":"0","operatorUser":"SystemOperator","remark":"","removed":0,"systemCode":"xpj","createTime":1505112145000,"domain":"https://www.xpj88879.com","lastModifyTime":1505112288000,"createUser":"SystemCreate","id":21},{"domainState":"0","operatorUser":"SystemOperator","remark":"","removed":0,"systemCode":"xpj","createTime":1505112250000,"domain":"https://www.xpj01666.com","lastModifyTime":1505112291000,"createUser":"SystemCreate","id":22},{"domainState":"0","operatorUser":"SystemOperator","remark":"","removed":0,"systemCode":"xpj","createTime":1505112334000,"domain":"https://www.xpj07999.com","lastModifyTime":1505112334000,"createUser":"SystemCreate","id":23},{"domainState":"0","operatorUser":"SystemOperator","remark":"","removed":0,"systemCode":"xpj","createTime":1505112376000,"domain":"https://www.pj77000.com","lastModifyTime":1505112376000,"createUser":"SystemCreate","id":24}]
     */

    private int stat;
    private List<DataBean> data;

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * domainState : 0
         * operatorUser : SystemOperator
         * remark :
         * removed : 0
         * systemCode : xpj
         * createTime : 1505111973000
         * domain : https://www.pj78777.com
         * lastModifyTime : 1505112284000
         * createUser : SystemCreate
         * id : 20
         */

        private String domainState;
        private String operatorUser;
        private String remark;
        private int removed;
        private String systemCode;
        private long createTime;
        private String domain;
        private long lastModifyTime;
        private String createUser;
        private String id;

        public String getDomainState() {
            return domainState;
        }

        public void setDomainState(String domainState) {
            this.domainState = domainState;
        }

        public String getOperatorUser() {
            return operatorUser;
        }

        public void setOperatorUser(String operatorUser) {
            this.operatorUser = operatorUser;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getRemoved() {
            return removed;
        }

        public void setRemoved(int removed) {
            this.removed = removed;
        }

        public String getSystemCode() {
            return systemCode;
        }

        public void setSystemCode(String systemCode) {
            this.systemCode = systemCode;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public long getLastModifyTime() {
            return lastModifyTime;
        }

        public void setLastModifyTime(long lastModifyTime) {
            this.lastModifyTime = lastModifyTime;
        }

        public String getCreateUser() {
            return createUser;
        }

        public void setCreateUser(String createUser) {
            this.createUser = createUser;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
