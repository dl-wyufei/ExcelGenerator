package com.kunpeng.jsonmodel;

import java.util.Collections;
import java.util.List;

/**
 * 整体数据存储结构
 * 
 * @author dl_wyufei
 * 
 */
public class MData {

  /** 数据名称 */
  private String dataName = null;
  /** 数据内大类集合 */
  private List<MCategory> categories = null;

  public MData() {}

  /**
   * 设置数据的名称
   */
  public void setDataName(String name) {
    this.dataName = name;
  }

  /**
   * 设置大类数据
   */
  public void setCategories(List<MCategory> categories) {
    this.categories = categories;
  }

  /**
   * 获取数据名称
   */
  public String getName() {
    return this.dataName;
  }

  /**
   * 获取大类数据
   * 
   * @return 不可变集合
   */
  public List<MCategory> getCategories() {
    if (categories == null) {
      return null;
    } else {
      return Collections.unmodifiableList(categories);
    }
  }

}
