package com.kunpeng.base;

import java.util.Collections;
import java.util.List;

/**
 * Base-Structure of Primary Data
 * 
 * @author dl_wyufei
 */
public class MData {

  private String dataName = null;
  private List<MCategory> categories = null;

  private String localName = null;

  private boolean isEncrypted = false;

  public boolean isEncrypted() {
    return isEncrypted;
  }

  public void setEncrypted(boolean isEncrypt) {
    this.isEncrypted = isEncrypt;
  }

  public MData() {}

  public void setDataName(String name) {
    this.dataName = name;
  }

  public void setCategories(List<MCategory> categories) {
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
  
  public List<MCategory> getCategoriesForSer() {
    return categories;
  }
  
  public void setLocalName(String localName) {
    this.localName = localName;
  }

  public String getLocalName() {
    return this.localName;
  }

}
