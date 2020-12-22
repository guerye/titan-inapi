package com.riozenc.api.webapp.service;

import com.riozenc.titanTool.spring.web.http.HttpResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

public interface IBillingService {


    public HttpResult applyBackChargeBySettleForBank(Map<String, String> params);

    public HttpResult saveBackCharge(Map<String, Object> params);

    public HttpResult backCharge(Map<String, String> params);

    public Map<String, Object> findChargeInfoForDz(Map<String, String> params);

}
