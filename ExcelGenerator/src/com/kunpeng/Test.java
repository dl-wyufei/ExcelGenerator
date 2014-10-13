package com.kunpeng;

import java.io.File;

import com.kunpeng.xlsx.XlsxCreator;



public class Test {

  public static void main(String[] args) {
    File[] fs = (new JsonFileReader("D:/inp")).getFiles();

    XlsxCreator creator = new XlsxCreator(fs);
    creator.create(XlsxCreator.XLSX_SEP_SHEET);
  }


}
