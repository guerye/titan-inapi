package com.riozenc.api.webapp.domain.YzcxBank;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;

public class YzcxBody {

    @JSONField(name="PAY_ID")
    private String payId;// 结算户编号 SETTLEMENT_NO varchar(16) 16 FALSE FALSE FALSE
    @JSONField(name="DL_TERM")
    private String dlTerm;//账期用
    @JSONField(name="CUSTNAME")
    private String custname;
    @JSONField(name="HOME_ADDR")
    private String homeAddr;
    @JSONField(name="TOTAL_AMT")
    private BigDecimal totalAmt;
    @JSONField(name="TRAN_AMT")
    private BigDecimal tranAmt;
    @JSONField(name="BALANCE")
    private BigDecimal Balance;
    @JSONField(name="DETAILNUM")
    private Integer detailnum;
    @JSONField(name="STR10")
    private String str10;
    @JSONField(name="STR17")
    private String str17;
    @JSONField(name="STR30")
    private String str30;

    @JSONField(name="TX_AMOUNT")
    private BigDecimal txAmount;
    @JSONField(name="BILL_NO")
    private String billNo;
    @JSONField(name="TX_SERIAL_NO")
    private String txSerialNo;

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getDlTerm() {
        return dlTerm;
    }

    public void setDlTerm(String dlTerm) {
        this.dlTerm = dlTerm;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getHomeAddr() {
        return homeAddr;
    }

    public void setHomeAddr(String homeAddr) {
        this.homeAddr = homeAddr;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(BigDecimal tranAmt) {
        this.tranAmt = tranAmt;
    }

    public BigDecimal getBalance() {
        return Balance;
    }

    public void setBalance(BigDecimal balance) {
        Balance = balance;
    }

    public Integer getDetailnum() {
        return detailnum;
    }

    public void setDetailnum(Integer detailnum) {
        this.detailnum = detailnum;
    }

    public String getStr17() {
        return str17;
    }

    public void setStr17(String str17) {
        this.str17 = str17;
    }

    public String getStr10() {
        return str10;
    }

    public void setStr10(String str10) {
        this.str10 = str10;
    }

    public BigDecimal getTxAmount() {
        return txAmount;
    }

    public void setTxAmount(BigDecimal txAmount) {
        this.txAmount = txAmount;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getTxSerialNo() {
        return txSerialNo;
    }

    public void setTxSerialNo(String txSerialNo) {
        this.txSerialNo = txSerialNo;
    }

    public String getStr30() {
        return str30;
    }

    public void setStr30(String str30) {
        this.str30 = str30;
    }
}
