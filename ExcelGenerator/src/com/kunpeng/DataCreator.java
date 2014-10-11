package com.kunpeng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.kunpeng.model.DeviceData;
import com.kunpeng.model.GroupData;
import com.kunpeng.model.HeadData;

import net.sf.json.JSONObject;

/**
 * 用于构建xlsx的数据生成
 * 
 * @author harrisonwang
 */
public class DataCreator {

  private HashMap<String, String> source = null;

  private HeadData headData = null;

  public DataCreator(HashMap<String, String> source) {
    headData = new HeadData();
    this.source = source;
  }

  /** 获取最终生成xlsx所需数据 */
  public List<DeviceData> getData() {
    List<DeviceData> data = new ArrayList<DeviceData>();

    for (String deviceModel : source.keySet()) {
      data.add(parseJson(deviceModel, source.get(deviceModel)));
    }

    return data;
  }

  public HeadData getHeadData() {
    return this.headData;
  }

  /**
   * 单条Json数据解析并存储数据
   */
  @SuppressWarnings("unchecked")
  private DeviceData parseJson(String model, String json) {

    DeviceData deviceData = new DeviceData(model);

    JSONObject primary = JSONObject.fromObject(json);// 整个Json对象
    Set<String> groupNames = primary.keySet();// 大类名称集合

    for (String groupName : groupNames) {// 遍历大类
      GroupData groupData = new GroupData(groupName);

      JSONObject group = primary.getJSONObject(groupName);// 一个大类内保存数据的Json对象
      Set<String> detailNames = group.keySet();

      headData.addHeaders(groupName, detailNames);

      for (String detailName : detailNames) {
        String value = null;
        if ((value = group.getString(detailName)) == null) {
          value = "null";
        }
        groupData.addContent(value);
      }
      deviceData.addGroupData(groupData);
    }

    return deviceData;
  }
}
