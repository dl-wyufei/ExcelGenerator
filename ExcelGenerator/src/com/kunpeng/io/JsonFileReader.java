package com.kunpeng.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class JsonFileReader {

  public File[] getFiles(File allocFile) {
    if (allocFile.isDirectory()) {
      return allocFile.listFiles();
    } else {
      File[] singleFile = new File[] {allocFile};
      return singleFile;
    }
  }

  public String read(File file) throws IOException {
    BufferedReader br = new BufferedReader(new FileReader(file));
    StringBuffer sb = new StringBuffer(getLabel(file));

    String temp = null;
    temp = br.readLine();
    while (temp != null) {
      sb.append(temp);
      temp = br.readLine();
    }
    br.close();
    return sb.toString();
  }

  public String getLabel(File file) {
    String fileName = file.getName();
    int beginIndex = fileName.indexOf("[");
    int endIndex = fileName.lastIndexOf("]");
    if (beginIndex == -1 || endIndex == -1 || beginIndex > endIndex) {
      return "-";
    }
    return fileName.substring(beginIndex + 1, endIndex);
  }

}
