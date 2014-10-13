package com.kunpeng;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class JsonFileReader {

  private HashMap<String, String> source = new HashMap<String, String>();

  private String folderPath = null;

  public JsonFileReader(String folder) {
    this.folderPath = folder;
  }

  public HashMap<String, String> getSource() {
    for (File file : getFiles()) {
      try {
        getJsonByFile(file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return source;
  }

  public File[] getFiles() {
    if (folderPath != null) {
      File folderFile = new File(folderPath);
      if (folderFile.isDirectory()) {
        return folderFile.listFiles();
      } else {
        return new File[] {folderFile};
      }
    } else {
      return null;
    }
  }

  private void getJsonByFile(File file) throws IOException {
    if (!file.exists() || file.isDirectory()) {
      throw new FileNotFoundException();
    }
    BufferedReader br = new BufferedReader(new FileReader(file));
    String temp = null;
    StringBuffer sb = new StringBuffer();
    temp = br.readLine();
    while (temp != null) {
      sb.append(temp + " ");
      temp = br.readLine();
    }
    br.close();
    source.put(file.getName(), sb.toString());
  }
}
