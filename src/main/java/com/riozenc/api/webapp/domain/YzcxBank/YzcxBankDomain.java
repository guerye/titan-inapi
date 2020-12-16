package com.riozenc.api.webapp.domain.YzcxBank;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class YzcxBankDomain {
    @JSONField(ordinal = 3)
    private YzcxHead head;
    @JSONField(ordinal = 1)
    private YzcxBody body;
    @JSONField(name="Ext",ordinal = 2)
    private YzcxExt Ext;



    public YzcxHead getHead() {
        return head;
    }

    public void setHead(YzcxHead head) {
        this.head = head;
    }

    public YzcxBody getBody() {
        return body;
    }

    public void setBody(YzcxBody body) {
        this.body = body;
    }

    public YzcxExt getExt() {
        return Ext;
    }

    public void setExt(YzcxExt ext) {
        Ext = ext;
    }
}
