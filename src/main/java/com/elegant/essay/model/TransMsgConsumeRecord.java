package com.elegant.essay.model;

import java.util.Date;

public class TransMsgConsumeRecord {
    private Long id;

    private String topic;

    private Integer flag;

    private String msgBody;

    private String msgId;

    private Date bornTime;

    private Date storeTime;

    private Integer reconsumeTimes;

    private Integer queueId;

    private Integer sysFlag;

    private String transId;

    private Integer transState;

    private String msgKeys;

    private Boolean msgIsTran;

    private Integer msgTranCheckTimes;

    private String msgUniqKey;

    private Boolean msgIsWait;

    private String msgGroup;

    private String msgTags;

    private Integer msgRealQueueId;

    private Integer bizType;

    private Date createTime;

    private Date updateTime;

    public TransMsgConsumeRecord(Long id, String topic, Integer flag, String msgBody, String msgId, Date bornTime, Date storeTime, Integer reconsumeTimes, Integer queueId, Integer sysFlag, String transId, Integer transState, String msgKeys, Boolean msgIsTran, Integer msgTranCheckTimes, String msgUniqKey, Boolean msgIsWait, String msgGroup, String msgTags, Integer msgRealQueueId, Integer bizType, Date createTime, Date updateTime) {
        this.id = id;
        this.topic = topic;
        this.flag = flag;
        this.msgBody = msgBody;
        this.msgId = msgId;
        this.bornTime = bornTime;
        this.storeTime = storeTime;
        this.reconsumeTimes = reconsumeTimes;
        this.queueId = queueId;
        this.sysFlag = sysFlag;
        this.transId = transId;
        this.transState = transState;
        this.msgKeys = msgKeys;
        this.msgIsTran = msgIsTran;
        this.msgTranCheckTimes = msgTranCheckTimes;
        this.msgUniqKey = msgUniqKey;
        this.msgIsWait = msgIsWait;
        this.msgGroup = msgGroup;
        this.msgTags = msgTags;
        this.msgRealQueueId = msgRealQueueId;
        this.bizType = bizType;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public TransMsgConsumeRecord() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getMsgBody() {
        return msgBody;
    }

    public void setMsgBody(String msgBody) {
        this.msgBody = msgBody == null ? null : msgBody.trim();
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    public Date getBornTime() {
        return bornTime;
    }

    public void setBornTime(Date bornTime) {
        this.bornTime = bornTime;
    }

    public Date getStoreTime() {
        return storeTime;
    }

    public void setStoreTime(Date storeTime) {
        this.storeTime = storeTime;
    }

    public Integer getReconsumeTimes() {
        return reconsumeTimes;
    }

    public void setReconsumeTimes(Integer reconsumeTimes) {
        this.reconsumeTimes = reconsumeTimes;
    }

    public Integer getQueueId() {
        return queueId;
    }

    public void setQueueId(Integer queueId) {
        this.queueId = queueId;
    }

    public Integer getSysFlag() {
        return sysFlag;
    }

    public void setSysFlag(Integer sysFlag) {
        this.sysFlag = sysFlag;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public Integer getTransState() {
        return transState;
    }

    public void setTransState(Integer transState) {
        this.transState = transState;
    }

    public String getMsgKeys() {
        return msgKeys;
    }

    public void setMsgKeys(String msgKeys) {
        this.msgKeys = msgKeys == null ? null : msgKeys.trim();
    }

    public Boolean getMsgIsTran() {
        return msgIsTran;
    }

    public void setMsgIsTran(Boolean msgIsTran) {
        this.msgIsTran = msgIsTran;
    }

    public Integer getMsgTranCheckTimes() {
        return msgTranCheckTimes;
    }

    public void setMsgTranCheckTimes(Integer msgTranCheckTimes) {
        this.msgTranCheckTimes = msgTranCheckTimes;
    }

    public String getMsgUniqKey() {
        return msgUniqKey;
    }

    public void setMsgUniqKey(String msgUniqKey) {
        this.msgUniqKey = msgUniqKey == null ? null : msgUniqKey.trim();
    }

    public Boolean getMsgIsWait() {
        return msgIsWait;
    }

    public void setMsgIsWait(Boolean msgIsWait) {
        this.msgIsWait = msgIsWait;
    }

    public String getMsgGroup() {
        return msgGroup;
    }

    public void setMsgGroup(String msgGroup) {
        this.msgGroup = msgGroup == null ? null : msgGroup.trim();
    }

    public String getMsgTags() {
        return msgTags;
    }

    public void setMsgTags(String msgTags) {
        this.msgTags = msgTags == null ? null : msgTags.trim();
    }

    public Integer getMsgRealQueueId() {
        return msgRealQueueId;
    }

    public void setMsgRealQueueId(Integer msgRealQueueId) {
        this.msgRealQueueId = msgRealQueueId;
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}