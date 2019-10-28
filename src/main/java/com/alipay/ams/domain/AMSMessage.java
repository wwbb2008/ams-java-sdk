/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package com.alipay.ams.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alipay.ams.cfg.AMSSettings;

/**
 * 
 * @author guangling.zgl
 * @version $Id: AMSMessage.java, v 0.1 2019年10月16日 上午11:49:48 guangling.zgl Exp $
 */
public abstract class AMSMessage {

    private AMSSettings settings;
    private String      requestURI;

    protected AMSMessage(String requestURI, AMSSettings settings) {
        this.requestURI = requestURI;
        this.settings = settings;
    }

    /**
     * For log message
     * 
     * @return
     */
    public abstract String getBizIdentifier();

    /**
     * 
     * @return
     */
    public String getRequestURI() {
        return requestURI;
    }

    /**
     * Getter method for property <tt>settings</tt>.
     * 
     * @return property value of settings
     */
    public AMSSettings getSettings() {
        return settings;
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}