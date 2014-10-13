package com.kunpeng.model;

import java.util.HashMap;

/**
 * Base-Structure of Category Data
 * 
 * @author dl_wyufei
 */
public abstract class MCategory {

  private String categoryName = null;
  private HashMap<String, String> content = null;

  public MCategory(String name, HashMap<String, String> content) {
    this.categoryName = name;
    this.content = content;
  }

  public String getName() {
    return this.categoryName;
  }

  public String getContent(String key) {
    if (this.content == null) {
      return "N/A";
    }
    String value = this.content.get(key);
    if (value == null) {
      return "N/A";
    }
    return value;
  }
}
