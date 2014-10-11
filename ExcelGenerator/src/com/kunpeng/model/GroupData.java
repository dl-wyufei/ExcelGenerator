package com.kunpeng.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个大类的数据(CPU 、GPU...)
 * 
 * @author harrisonwang
 */
public class GroupData {

  /** 大类名称 */
  private String groupName = null;
  /** 数据 */
  private List<String> content = null;

  public GroupData(String groupName) {
    this.groupName = groupName;
    content = new ArrayList<String>();
  }

  public String getGroupName() {
    return groupName;
  }

  public void addContent(String content) {
    this.content.add(content);
  }

  public String get(int index) {
    return content.get(index);
  }

}
