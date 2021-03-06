package org.zhenchao.builder;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 信息
 *
 * @author zhenchao.wang 2016-10-19 22:47
 * @version 1.0.0
 */
public class Message implements Product {

    private String receiver;
    private String subject;
    private String body;
    private Object attachment;
    private String sender;

    public Message() {
    }

    public Message(String receiver, String body, String sender) {
        this.receiver = receiver;
        this.body = body;
        this.sender = sender;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public Object getAttachment() {
        return attachment;
    }

    public String getSender() {
        return sender;
    }
}
