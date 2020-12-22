package com.riozenc.api.webapp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.riozenc.api.webapp.domain.ChargeDomain;
import com.riozenc.api.webapp.service.IBillingService;
import com.riozenc.titanTool.annotation.TransactionService;
import com.riozenc.titanTool.spring.web.http.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TransactionService
public class BillingServiceImpl implements IBillingService {
    @Autowired
    private RestTemplate restTemplate;



    @Override
    public HttpResult applyBackChargeBySettleForBank(Map<String, String> params){
        String realUrl = "http://BILLING-SERVER/billingServer/backCharge/applyBackChargeBySettleForBank";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);
        String json = restTemplate.postForObject(realUrl, httpEntity, String.class);
        HttpResult httpResult = JSONObject.parseObject(json, HttpResult.class);

        return httpResult;
    };

    public HttpResult saveBackCharge(Map<String,Object> map){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(map, httpHeaders);
        String json = restTemplate.postForObject("http://BILLING-SERVER/billingServer/backCharge/saveBackCharge", httpEntity, String.class);
        HttpResult httpResult = JSONObject.parseObject(json, HttpResult.class);

        return httpResult;
    }
    public HttpResult backCharge(Map<String,String> map){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(map, httpHeaders);
        String json = restTemplate.postForObject("http://BILLING-SERVER/billingServer/backCharge/backCharge", httpEntity, String.class);
        HttpResult httpResult = JSONObject.parseObject(json, HttpResult.class);

        return httpResult;
    }

    public Map<String, Object> findChargeInfoForDz(Map<String, String> params){
        String realUrl = "http://BILLING-SERVER/billingServer/charge/findChargeInfoForDz";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> httpEntity = new HttpEntity<>(params, httpHeaders);
        String json = restTemplate.postForObject(realUrl, httpEntity, String.class);
        Map<String, Object> jsonMap = JSONObject.parseObject(json, HashMap.class);
        return jsonMap;
    }
}
