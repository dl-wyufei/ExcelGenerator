package com.kunpeng;

import java.util.ArrayList;
import java.util.List;

public class Test {

  public static void main(String[] args) {
    long startTime = System.currentTimeMillis();
    System.out.println("开始时间" + startTime);

    List<List<String>> source = new ArrayList<List<String>>();

    // 表头
    List<String> head = new ArrayList<String>();
    head.add("测试表头 - 1");
    head.add("测试表头 - 2");
    head.add("测试表头测试表头测试表头 - 3");
    head.add("测试表头 - 4");
    head.add("测试表头 - 5");
    source.add(head);

    for (int i = 0; i < 15; i++) {// 十五条数据
      List<String> data = new ArrayList<String>();
      for (int j = 0; j < 5; j++) {
        data.add(i + " - " + j);
      }
      source.add(data);
    }

    XlsBuilder xlsBuilder = new XlsBuilder(source);
    xlsBuilder.build("D:/haha");
  }
}
