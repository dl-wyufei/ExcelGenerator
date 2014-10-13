package com.kunpeng.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class WorkBookInfo {

  private List<List<String>> headerNames = null;
  private List<String> categoriesNames = null;

  public WorkBookInfo() {
    headerNames = new ArrayList<List<String>>();
    categoriesNames = new ArrayList<String>();
  }

  public List<List<String>> getHeaderNames() {
    return headerNames;
  }

  public void addCategoryContentHeaders(List<String> headerName) {
    this.headerNames.add(headerName);
  }

  public List<String> getCategoryContentHeaders(int index) throws IndexOutOfBoundsException {
    return headerNames.get(index);
  }

  /** 获取分类列表 */
  public List<String> getCategoriesNames() {
    return Collections.unmodifiableList(categoriesNames);
  }

  /** 添加分类 */
  public void addCategoryName(String name) {
    categoriesNames.add(name);
  }

  public void setSheetNames(List<String> sheetNames) {
    this.categoriesNames = sheetNames;
  }

  public boolean isContainSheet(String sheetName) {
    return this.categoriesNames.contains(sheetName);
  }

}
