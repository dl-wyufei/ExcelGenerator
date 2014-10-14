package com.kunpeng;

import com.kunpeng.xlsx.XlsxCreator;



public class Test {

  public static void main(String[] args) {
    
    XlsxCreator creator = new XlsxCreator("D:/inp", "D:/303.xlsx", XlsxCreator.XLSX_ALL_IN_ONE);
    
    creator.create();
    
  }


}
