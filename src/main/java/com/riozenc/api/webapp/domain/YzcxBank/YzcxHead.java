package com.riozenc.api.webapp.domain.YzcxBank;

import com.alibaba.fastjson.annotation.JSONField;

public class YzcxHead {
    @JSONField(name="OUTSYS_TX_CODE")
    private String outsysTxCode;
    @JSONField(name="CLI_SERIAL_NO")
    private String cliSerialNo;
    @JSONField(name="LOCALTIME")
    private String localtime;
    @JSONField(name="LOCALDATE")
    private String localdate;
    @JSONField(name="CLEARDATE")
    private String cleardate;
    @JSONField(name="RESPCODE")
    private String respcode;
    @JSONField(name="RET_EXPLAIN")
    private String retExplain;

    public String getOutsysTxCode() {
        return outsysTxCode;
    }

    public void setOutsysTxCode(String outsysTxCode) {
        this.outsysTxCode = outsysTxCode;
    }

    public String getCliSerialNo() {
        return cliSerialNo;
    }

    public void setCliSerialNo(String cliSerialNo) {
        this.cliSerialNo = cliSerialNo;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltime(String localtime) {
        this.localtime = localtime;
    }

    public String getLocaldate() {
        return localdate;
    }

    public void setLocaldate(String localdate) {
        this.localdate = localdate;
    }

    public String getCleardate() {
        return cleardate;
    }

    public void setCleardate(String cleardate) {
        this.cleardate = cleardate;
    }

    public String getRespcode() {
        return respcode;
    }

    public void setRespcode(String respcode) {
        this.respcode = respcode;
    }

    public String getRetExplain() {
        return retExplain;
    }

    public void setRetExplain(String retExplain) {
        this.retExplain = retExplain;
    }
}
