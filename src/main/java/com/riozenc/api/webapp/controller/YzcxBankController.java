package com.riozenc.api.webapp.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.riozenc.api.webapp.domain.ArrearageDetailDomain;
import com.riozenc.api.webapp.domain.ChargeDomain;
import com.riozenc.api.webapp.domain.SettlementDomain;
import com.riozenc.api.webapp.domain.YzcxBank.STR2;
import com.riozenc.api.webapp.domain.YzcxBank.YzcxBankDomain;
import com.riozenc.api.webapp.domain.YzcxBank.YzcxExt;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


@Controller
@RequestMapping("yzcxBank")
public class YzcxBankController {
    private Log logger = LogFactory.getLog(YzcxBankController.class);

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("findArrearageDetail")
    @ResponseBody
    public String findArrearageDetail(@RequestBody String DomainJson) throws IOException {
        YzcxBankDomain yzcxBankDomain=null;
    try{
        yzcxBankDomain = JSONObject.parseObject(DomainJson, YzcxBankDomain.class);
        if(yzcxBankDomain.getExt()!=null){
            yzcxBankDomain.getExt().setSTR1(null);
        }else{
            YzcxExt emptyExt=new YzcxExt();
            yzcxBankDomain.setExt(emptyExt);
        }
        if(!"983000".equals(yzcxBankDomain.getHead().getOutsysTxCode())){
            yzcxBankDomain.getHead().setRespcode("000001");
            yzcxBankDomain.getHead().setRetExplain("业务种类错误");
            return  JSON.toJSONString(yzcxBankDomain);
        }
        Map<String, String> params = new HashMap<>();
        params.put("chargeObject", "3");
        params.put("no",yzcxBankDomain.getBody().getPayId() );


        String realUrl = "http://BILLING-SERVER/billingServer/arrearage/findArrearageDetail";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);
        String json = restTemplate.postForObject(realUrl, httpEntity, String.class);

        Map<String, Object> jsonMap = JSONObject.parseObject(json, HashMap.class);
        Map<String, Object> returnMap = (Map<String, Object>) jsonMap.get("resultData");
        if("500".equals(jsonMap.get("statusCode").toString())){
            if("结算户输入错误,该结算户下没有计量点".equals(jsonMap.get("message").toString())){
                yzcxBankDomain.getHead().setRespcode("0004");
                yzcxBankDomain.getHead().setRetExplain("该结算户下没有计量点");
                return JSON.toJSONString(yzcxBankDomain);
            }
            if("该结算户号不存在或者该结算户号存在重复记录".equals(jsonMap.get("message").toString())){
                yzcxBankDomain.getHead().setRespcode("0002");
                yzcxBankDomain.getHead().setRetExplain("该结算户号不存在或者该结算户号存在重复记录");
                return  JSON.toJSONString(yzcxBankDomain);
            }
        }
        List<ArrearageDetailDomain> arrearageDetailDomainList=JSONObject.parseArray(JSONObject.toJSONString(returnMap.get("arrearageDetails")),ArrearageDetailDomain.class);
        if(arrearageDetailDomainList.size()==0){
            yzcxBankDomain.getHead().setRespcode("0003");
            yzcxBankDomain.getHead().setRetExplain("该账户不存在欠费信息!");
            return  JSON.toJSONString(yzcxBankDomain);
        }


        SettlementDomain settlementDomain=JSONObject.parseObject(JSONObject.toJSONString(returnMap.get("settement")), SettlementDomain.class);


        yzcxBankDomain.getBody().setCustname(settlementDomain.getSettlementName());
        yzcxBankDomain.getBody().setHomeAddr(settlementDomain.getAddress());
        yzcxBankDomain.getBody().setTotalAmt(arrearageDetailDomainList.stream().map(ArrearageDetailDomain::getOweMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
        yzcxBankDomain.getBody().setTranAmt(arrearageDetailDomainList.stream().map(ArrearageDetailDomain::getOweMoney).reduce(BigDecimal.ZERO, BigDecimal::add));
        yzcxBankDomain.getBody().setDetailnum(arrearageDetailDomainList.size());
        yzcxBankDomain.getBody().setBalance(BigDecimal.valueOf(JSONObject.parseObject(returnMap.get("lastBalance").toString(),Double.class)));

        String arrearageIds="";
        StringBuffer stringBuffer=new StringBuffer();
        for (ArrearageDetailDomain arrearageDetailDomain : arrearageDetailDomainList) {
            stringBuffer.append(arrearageDetailDomain.getMon());
            stringBuffer.append("月滞纳金");
            stringBuffer.append(arrearageDetailDomain.getPunishMoney());
            stringBuffer.append("元,用电量");
            stringBuffer.append(arrearageDetailDomain.getTotalPower());
            stringBuffer.append("千瓦时,电费");
            stringBuffer.append(arrearageDetailDomain.getAmount());
            stringBuffer.append("元;");
            arrearageIds+=arrearageDetailDomain.getId()+",";
        }
        arrearageIds=arrearageIds.substring(0,arrearageIds.length()-1);
        yzcxBankDomain.getBody().setStr30(arrearageIds.toString());


        STR2 str2=new STR2();
        str2.setSTR39(settlementDomain.getSettlementName());
//        str2.setAMT10(returnMap.get("lastBalance").toString());
        str2.setAMT11(returnMap.get("lastBalance").toString());
        str2.setSTR40(settlementDomain.getSettlementNo());
        str2.setSTR43(arrearageDetailDomainList.stream().map(ArrearageDetailDomain::getPunishMoney).reduce(BigDecimal.ZERO,BigDecimal::add).toString());
        str2.setSTR44(stringBuffer.toString());
        str2.setSTR45(String.valueOf(arrearageDetailDomainList.stream().filter(a->a.getTotalPower()!=null&&!"".equals(a.getTotalPower())).mapToDouble(ArrearageDetailDomain::getTotalPower).sum()));
        str2.setSTR46(arrearageDetailDomainList.stream().filter(a->a.getAmount()!=null&&!"".equals(a.getAmount())).map(ArrearageDetailDomain::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add).toString());

        yzcxBankDomain.getExt().setSTR2(str2);
    }catch (Exception e){
        e.printStackTrace();
        yzcxBankDomain.getHead().setRespcode("DFT");
        yzcxBankDomain.getHead().setRetExplain("未知错误");
        return JSON.toJSONString(yzcxBankDomain);
    }
        yzcxBankDomain.getHead().setRespcode("0000");
        return  JSON.toJSONString(yzcxBankDomain);
    }

    @RequestMapping("yzcxPay")
    @ResponseBody
    public String yzcxPay(@RequestBody String DomainJson) throws IOException {
        logger.info("缴费传入"+DomainJson);
        YzcxBankDomain yzcxBankDomain = null;
        try {
            yzcxBankDomain = JSONObject.parseObject(DomainJson, YzcxBankDomain.class);

            if (!"983010".equals(yzcxBankDomain.getHead().getOutsysTxCode())) {
                yzcxBankDomain.getHead().setRespcode("000001");
                yzcxBankDomain.getHead().setRetExplain("业务种类错误");
                return JSON.toJSONString(yzcxBankDomain);
            }

            Map<String, Object> countData = new HashMap<>();
            Map<String, Object> params = new HashMap<>();
            if(yzcxBankDomain.getBody().getStr30()!=null&&!"".equals(yzcxBankDomain.getBody().getStr30())){
                params.put("ids", Arrays.asList(yzcxBankDomain.getBody().getStr30().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
            }else{
                yzcxBankDomain.getHead().setRespcode("0000D2");
                yzcxBankDomain.getHead().setRetExplain("未传欠费单号");
                return JSON.toJSONString(yzcxBankDomain);
            }

            params.put("fChargeMode", "11");
            params.put("settleMentNo", yzcxBankDomain.getBody().getPayId());
            params.put("billNo",yzcxBankDomain.getBody().getBillNo());
            params.put("operator",999934);
            String realUrl = "http://BILLING-SERVER/billingServer/bankInterfaceController/fullCharge";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);
            String json = restTemplate.postForObject(realUrl, httpEntity, String.class);
            Map<String, Object> jsonMap = JSONObject.parseObject(json, HashMap.class);
            if("500".equals(jsonMap.get("statusCode").toString())){
                if("无对应的欠费记录".equals(jsonMap.get("message").toString())){
                    yzcxBankDomain.getHead().setRespcode("0000E5");
                    yzcxBankDomain.getHead().setRetExplain("无对应的欠费记录");
                    return JSON.toJSONString(yzcxBankDomain);
                }else if("没有要全额扣款的户".equals(jsonMap.get("message").toString())){
                    yzcxBankDomain.getHead().setRespcode("0000D2");
                    yzcxBankDomain.getHead().setRetExplain("没有要全额扣款的户");
                    return JSON.toJSONString(yzcxBankDomain);
                }else{

                }
            }
            List<ChargeDomain> chargeDomainList=JSONObject.parseArray(JSONObject.toJSONString(jsonMap.get("resultData")),ChargeDomain.class);
            logger.info("缴费返回"+JSON.toJSON(chargeDomainList));
            yzcxBankDomain.getBody().setTxSerialNo(chargeDomainList.get(0).getFlowNo());

        } catch (RestClientException e) {
            e.printStackTrace();
            yzcxBankDomain.getHead().setRespcode("DFT");
            yzcxBankDomain.getHead().setRetExplain("未知错误");
            return JSON.toJSONString(yzcxBankDomain);
        }
        yzcxBankDomain.getHead().setRespcode("0000");
        return JSON.toJSONString(yzcxBankDomain);
    }


    @RequestMapping("reconciliation")
    @ResponseBody
    public String reconciliation(@RequestBody String DomainJson) throws IOException {
        logger.info("对账传入"+DomainJson);
        YzcxBankDomain yzcxBankDomain = null;
        yzcxBankDomain = JSONObject.parseObject(DomainJson, YzcxBankDomain.class);
        List needCharge=new ArrayList();
        List needBack=new ArrayList();

        //获取文件


        //获取billing当日流水号
        Map<String, String> params = new HashMap<>();
        params.put("chargeObject", "3");
        params.put("no",yzcxBankDomain.getBody().getPayId() );
        String realUrl = "http://BILLING-SERVER/billingServer/charge/select";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);
        String json = restTemplate.postForObject(realUrl, httpEntity, String.class);
        Map<String, Object> jsonMap = JSONObject.parseObject(json, HashMap.class);
        List<ChargeDomain> returnMap = (List<ChargeDomain>) jsonMap.get("resultData");

        //对比



        //少的缴费入账
        if(needCharge.size()>0){

        }
        //多的抹账申请-审批（改状态）-抹账
        if(needBack.size()>0){

        }
        return JSON.toJSONString(yzcxBankDomain);
    }
}
