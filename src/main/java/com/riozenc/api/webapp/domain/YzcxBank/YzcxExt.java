package com.riozenc.api.webapp.domain.YzcxBank;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class YzcxExt {
    @JSONField(name="STR1")
    private String STR1;
    @JSONField(name="STR2")
    private STR2 STR2;

    public String getSTR1() {
        return STR1;
    }

    public void setSTR1(String STR1) {
        this.STR1 = STR1;
    }

    public com.riozenc.api.webapp.domain.YzcxBank.STR2 getSTR2() {
        return STR2;
    }

    public void setSTR2(com.riozenc.api.webapp.domain.YzcxBank.STR2 STR2) {
        this.STR2 = STR2;
    }
}
