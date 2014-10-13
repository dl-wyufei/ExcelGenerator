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

  public void build(String filePath) {

    // ==============> 构建分类表 <============
    // 获取大类集合
    ArrayList<String> groups = new ArrayList<String>();
    DeviceData tmp = data.get(0);
    for (int i = 0, lg = tmp.getContentSize(); i < lg; i++) {
      groups.add(tmp.get(i).getGroupName());
    }
    // 构建各个分类表
    for (int i = 0, lg = groups.size(); i < lg; i++) {
      String groupName = groups.get(i);
      Sheet sh = createSheet(groupName);

      List<String> headers = header.getHeaders(groupName);// 表头数据
      int columnCount = headers.size();// 列数

      ArrayList<Integer> Hlength = createHeader(sh, headers, columnCount);
      ArrayList<Integer> length = createRows(sh, data, columnCount, i);

      for (int j = 0; j < columnCount; j++) {// 所有列长度适配
        int len = length.get(j) + 1;
        int Hlen = Hlength.get(j) + 1;
        int flen = len;
        if (Hlen > flen) {
          flen = Hlen;
        }
        if(flen > 230){
          flen = 230;
        }
        sh.setColumnWidth(j, flen * 256 + 500);
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

  /** 创建表头 */
  private ArrayList<Integer> createHeader(Sheet sh, List<String> headers, int columnCount) {
    ArrayList<Integer> maxSizeCount = new ArrayList<Integer>(columnCount);
    Row row = sh.createRow(0);
    for (int i = 0; i < columnCount; i++) {
      Cell cell = row.createCell(i);
      cell.setCellValue(headers.get(i));

      int len = headers.get(i).getBytes().length;
      if (maxSizeCount.size() <= i) {
        maxSizeCount.add(len);
      } else if (maxSizeCount.get(i) < len) {
        maxSizeCount.set(i, len);
      }

      cell.setCellStyle(styleToolkit.getHeadStyle());
    }
    row.setHeight((short) (35 * 20));
    return maxSizeCount;
  }


  /** 创建一个分类工作表内的据 */
  private ArrayList<Integer> createRows(Sheet sh, List<DeviceData> data, int columnCount,
      int groupIndex) {
    ArrayList<Integer> maxSizeCount = new ArrayList<Integer>(columnCount);
    for (int i = 1, lg = (data.size() + 1); i < lg; i++) {
      DeviceData curDevData = data.get(i - 1);
      GroupData curGroupData = curDevData.get(groupIndex);
      Row row = sh.createRow(i);
      for (int j = 0; j < columnCount; j++) {
        Cell cell = row.createCell(j);
        System.out.println(curGroupData.getGroupName());
        cell.setCellValue(curGroupData.get(j));

        int len = curGroupData.get(j).getBytes().length;
        if (maxSizeCount.size() <= j) {
          maxSizeCount.add(len);
        } else if (maxSizeCount.get(j) < len) {
          maxSizeCount.set(j, len);
        }


        if (j % 2 == 0) {
          cell.setCellStyle(styleToolkit.getDataStyle_0());
        } else {
          cell.setCellStyle(styleToolkit.getDataStyle_1());
        }
      }
    }
    return maxSizeCount;
  }
}
