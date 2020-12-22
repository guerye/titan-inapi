package com.riozenc.api.util;

import com.riozenc.titanTool.properties.Global;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class File {
    /**
     * 逐行读取txt文件并写入到list实体类中
     *lineNum 跳过的行数
     */
    public static List<Map<String,String>> readTxts(String filePath,int lineNum) throws IOException {
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "gbk");
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        int count = 0;
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();

        for (int i=0;i<lineNum;i++){
            br.readLine();
        }

        while ((line = br.readLine()) != null) {
            Map<String,String> txt = new HashMap<String,String>();
            String[] arr = line.split("\\|");
                if(arr.length==1){
                    txt.put("0",arr[0]);
                }
                if(arr.length==2){
                txt.put("1",arr[1]);
                }
                if(arr.length==3) {
                txt.put("2", arr[2]);
                }
                if(arr.length==4){
                txt.put("3",arr[3]);
                }
                if(arr.length==5){
                txt.put("4",arr[4]);
                }
                if(arr.length==6){
                txt.put("5",arr[5]);
                }
                list.add(txt);

            count++;
        }
        System.out.println(list);
        System.out.println("读取总条数：" + count + "||读取的list的长度" + list.size());
        return list;
    }
}
