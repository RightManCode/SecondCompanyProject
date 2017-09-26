package com.wishland.www.xinwanhaotest.model.bean;

/**
 * Created by admin on 2017/9/11.
 */

public class DomainChangeBean {

    /**
     * id : 数据id
     * domainState : 域名状态(0正常1不正常)
     */

    private String id;
    private String domainState;
    private String systemCode;
    private String domain;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDomainState() {
        return domainState;
    }

    public void setDomainState(String domainState) {
        this.domainState = domainState;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
