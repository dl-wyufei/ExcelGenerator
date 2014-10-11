package com.kunpeng.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 一台设备的所有信息
 * 
 * @author harrisonwang
 */
public class DeviceData {

  /** 设备标识 */
  private String deviceModel = null;
  private List<GroupData> content = null;

  public DeviceData(String deviceModel) {
    this.deviceModel = deviceModel;
    this.content = new ArrayList<GroupData>();
  }

  public String getDeviceModel() {
    return deviceModel;
  }

  public void addGroupData(GroupData gData) {
    this.content.add(gData);
  }

  public GroupData get(int index) {
    return content.get(index);
  }

  public int getGroupDataSize() {
    return content.size();
  }
}
