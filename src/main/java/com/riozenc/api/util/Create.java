package com.riozenc.api.util;

import com.riozenc.api.webapp.domain.ArrearageDetailDomain;
import com.riozenc.titanTool.common.ClassDAOXmlUtil;

import java.io.IOException;

public class Create {
	public static void main(String[] args)  {
		try {
			ClassDAOXmlUtil.buildXML("C:\\Users\\czy\\git\\titan-billing\\src\\main\\java\\com\\riozenc\\billing\\webapp\\dao",
					ArrearageDetailDomain.class, "PRICE_EXECUTION_INFO");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("创建成功");
	}
	
}
