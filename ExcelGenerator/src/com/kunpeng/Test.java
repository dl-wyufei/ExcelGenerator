package com.kunpeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.kunpeng.model.MCategory;
import com.kunpeng.model.MData;
import com.kunpeng.model.TestC;
import com.kunpeng.model.TestD;


public class Test {

  public static void main(String[] args) {

    HashMap<String, String> hm = new HashMap<String, String>();
    
    hm.put("CA", "CAv");
    hm.put("CB", "CBv");
    MCategory mc1 = new TestC("A", hm);
    MCategory mc2 = new TestC("B", hm);
    MCategory mc3 = new TestC("A", hm);
    
    List<MCategory> cas = new ArrayList<MCategory>();
    cas.add(mc1);cas.add(mc2);cas.add(mc3);
    MData md = new TestD("D",cas);

    Gson gson = new Gson();
    String js = gson.toJson(md);

    System.out.println(js);

    // MainJFrame mainJFrame = new MainJFrame();
    // mainJFrame.setVisible(true);
    // long startTime = System.currentTimeMillis();
    // System.out.println("开始时间" + startTime);
    //
    // JsonFileReader jsonFileReader = new JsonFileReader("D:/inp");
    //
    // HashMap<String, String> source = jsonFileReader.getSource();
    // DataCreator dataCreator = new DataCreator(source);
    // List<DeviceData> data = dataCreator.getData();
    // HeadData header = dataCreator.getHeadData();
    //
    // XlsBuilder xlsBuilder = new XlsBuilder(data, header);
    // xlsBuilder.build("D:/机型数据统计");


    // List<List<String>> source = new ArrayList<List<String>>();
    //
    // // 表头
    // List<String> head = new ArrayList<String>();
    // head.add("测试表头 - 1");
    // head.add("测试表头 - 2");
    // head.add("测试表头测试表头测试表头 - 3");
    // head.add("测试表头 - 4");
    // head.add("测试表头 - 5");
    // source.add(head);
    //
    // for (int i = 0; i < 15; i++) {// 十五条数据
    // List<String> data = new ArrayList<String>();
    // for (int j = 0; j < 5; j++) {
    // data.add(i + " - " + j);
    // }
    // source.add(data);
    // }
    //
    // XlsBuilder xlsBuilder = new XlsBuilder(source);
    // xlsBuilder.build("D:/haha");
  }


}
