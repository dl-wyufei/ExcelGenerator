package com.kunpeng.model;

import java.util.Collections;
import java.util.List;

/**
 * Base-Structure of Primary Data
 * 
 * @author dl_wyufei
 */
public abstract class MData {

  private String dataName = null;
  private List<MCategory> categories = null;

  public MData(String name, List<MCategory> categories) {
    this.dataName = name;
    this.categories = categories;
  }

  public String getName() {
    return this.dataName;
  }

  public List<MCategory> getCategories() {
    if (categories == null) {
      return null;
    } else {
      return Collections.unmodifiableList(categories);
    }
  }

}
