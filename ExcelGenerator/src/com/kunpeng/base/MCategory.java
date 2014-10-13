package com.kunpeng.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * Base-Structure of Category Data
 * 
 * @author dl_wyufei
 */
public class MCategory {

  private String categoryName = null;
  private LinkedHashMap<String, String> content = null;

  private HashMap<String, String> localNames = null;

  public MCategory() {}

  public void setCategoryName(String name) {
    this.categoryName = name;
  }

  public void setContent(LinkedHashMap<String, String> content) {
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

  public void setLocalNames(HashMap<String, String> localNames) {
    this.localNames = localNames;
  }

  public String getLocalName(String key) {
    if (this.localNames == null) {
      return null;
    }
    return this.localNames.get(key);
  }

  public List<String> getNames() {
    if (this.content != null) {
      ArrayList<String> names = new ArrayList<String>();
      for (Entry<String, String> entry : this.content.entrySet()) {
        names.add(entry.getKey());
      }
      return names;
    }
    return null;
  }

}
