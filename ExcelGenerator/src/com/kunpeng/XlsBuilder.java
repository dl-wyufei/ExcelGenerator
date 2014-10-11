package com.kunpeng;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.kunpeng.model.DeviceData;
import com.kunpeng.model.GroupData;
import com.kunpeng.model.HeadData;


/**
 * POI官方文档 https://poi.apache.org/apidocs/org/apache/poi/xssf/streaming/SXSSFWorkbook .html
 * 
 * @author Yufei
 *
 */
public final class XlsBuilder {

  private SXSSFWorkbook workBook = null;
  private StyleToolkit styleToolkit = null;

  private List<DeviceData> data = null;
  private HeadData header = null;

  public XlsBuilder(List<DeviceData> data, HeadData header) {
    this(300, data, header);
  }

  /**
   * @param rowsInMemory 同时留存于内存中的数据条数,超出部分会暂存于临时文件中
   */
  public XlsBuilder(int rowsInMemory, List<DeviceData> data, HeadData header) {
    workBook = new SXSSFWorkbook(rowsInMemory);
    this.styleToolkit = new StyleToolkit(workBook);
    this.data = data;
    this.header = header;
  }

  // /** 构建总表所需数据 */
  // private List<HashMap<String, String>> getTotalTableData() {
  // // 本方法返回的可用数据
  // List<HashMap<String, String>> tData = new ArrayList<HashMap<String, String>>();
  //
  // // 遍历每个大类的数据
  // for (HashMap<String, HashMap<String, String>> gSrc : src) {
  // HashMap<String, String> gData = new HashMap<String, String>();
  // // 遍历一个大类数据的详细条目
  // for (String gKey : gSrc.keySet()) {
  // // 最终的详细数据
  // HashMap<String, String> detailData = gSrc.get(gKey);
  // for (String dKey : detailData.keySet()) {
  // gData.put(gKey + dKey, detailData.get(dKey));
  // }
  // }
  // tData.add(gData);
  // }
  //
  // return tData;
  // }


  public void build(String filePath) {
    // 构建总表
    // Sheet sh = createSheet("总表");
    // for (int i = 0; i < source.size(); i++) {
    // createRow(sh, i, source.get(i));
    // }
    // for (int i = 0, lg = getColumnCount(); i < lg; i++) {// 所有列长度适配
    // sh.setColumnWidth(i, source.get(0).get(i).getBytes().length * 256 + 300);
    // }

    // 构建分类表
    ArrayList<String> groups = new ArrayList<String>();
    DeviceData tmp = data.get(0);
    for (int i = 0, lg = tmp.getGroupDataSize(); i < lg; i++) {
      groups.add(tmp.get(i).getGroupName());
    }

    for (int i = 0, lg = groups.size(); i < lg; i++) {
      Sheet sh = createSheet(groups.get(i));
      for (DeviceData devData : data) {
        GroupData gData = devData.get(i);
        createRow(sh, i, gData, groups.get(i), header.getHeaders(groups.get(i)).size());
      }
    }



    try {
      FileOutputStream out = new FileOutputStream(filePath + ".xlsx");
      workBook.write(out);
      workBook.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 创建工作表 */
  private Sheet createSheet(String sheetName) {
    Sheet sh = workBook.createSheet(sheetName);
    sh.createFreezePane(0, 1, 0, 1);// 冻结第一行,创建表头
    return sh;
  }


  /** 创建一行数据 */
  private void createRow(Sheet sh, int rowIndex, GroupData data, String groupName, int columnCount) {
    Row row = sh.createRow(rowIndex);
    // 设置表头行高
    if (rowIndex == 0) {
      row.setHeight((short) (35 * 20));
    }

    for (int i = 0; i < columnCount; i++) {
      Cell cell = row.createCell(i);
      cell.setCellValue(data.get(i));
      if (rowIndex == 0) {// 设置表头内容和样式
        cell.setCellValue(header.getHeaders(groupName).get(i));
        cell.setCellStyle(styleToolkit.getHeadStyle());
      } else if (rowIndex % 2 == 0) {
        cell.setCellStyle(styleToolkit.getDataStyle_0());
      } else {
        cell.setCellStyle(styleToolkit.getDataStyle_1());
      }
    }

  }
}
