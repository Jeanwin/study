/*****************************
 * Copyright (c) 2014 by Zonekey Co. Ltd.  All rights reserved.
 ****************************/
package com.zonekey.study.common;

public class JsonMsg {

    public String id;
    public String operation;
    public String content;

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getOperation() {
	return operation;
    }

    public void setOperation(String operation) {
	this.operation = operation;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }

    @Override
    public String toString() {
	return "JsonMsg [id=" + id + ", operation=" + operation + ", content=" + content + "]";
    }

}
