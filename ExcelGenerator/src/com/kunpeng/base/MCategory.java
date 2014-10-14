package com.kunpeng.base;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * 大类数据结构
 * 
 * @author dl_wyufei
 * 
 */
public class MCategory {

  /** 大类数据名称 */
  private String categoryName = null;
  /** 大类数据具体名称 */
  private LinkedHashMap<String, String> content = null;

  public MCategory() {}

  /**
   * 设置大类数据名称
   */
  public void setCategoryName(String name) {
    this.categoryName = name;
  }

  /**
   * 设置大类的具体数据
   */
  public void setContent(LinkedHashMap<String, String> content) {
    this.content = content;
  }

  /**
   * 获取当前大类的名称
   */
  public String getName() {
    return this.categoryName;
  }

  /**
   * 根据键值获取对应的具体数据
   * @return 如果键值对应无数据返回"N/A"
   */
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

  /**
   * 获取大类键值的集合
   */
  public List<String> getKeys() {
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
