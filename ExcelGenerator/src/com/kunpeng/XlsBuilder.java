package com.kunpeng;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;


/**
 * POI官方文档 https://poi.apache.org/apidocs/org/apache/poi/xssf/streaming/SXSSFWorkbook .html
 * 
 * @author Yufei
 *
 */
public final class XlsBuilder {

  private SXSSFWorkbook workBook = null;
  private List<List<String>> source = null;

  private StyleToolkit styleToolkit = null;

  public XlsBuilder(List<List<String>> source) {
    this(300, source);
  }

  /**
   * @param rowsInMemory 同时留存于内存中的数据条数,超出部分会暂存于临时文件中
   */
  public XlsBuilder(int rowsInMemory, List<List<String>> source) {
    workBook = new SXSSFWorkbook(rowsInMemory);
    this.source = source;
    this.styleToolkit = new StyleToolkit(workBook);
  }

  public void build(String filePath) {
    Sheet sh = createSheet("采集数据");
    for (int i = 0; i < source.size(); i++) {
      createRow(sh, i, source.get(i));
    }
    for (int i = 0, lg = getColumnCount(); i < lg; i++) {// 所有列长度适配
      sh.setColumnWidth(i, source.get(0).get(i).getBytes().length * 256 + 300);
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

  /** 获取列数 */
  private int getColumnCount() {
    return source.get(0).size();
  }

  /** 创建一行数据 */
  private void createRow(Sheet sh, int rowIndex, List<String> data) {
    Row row = sh.createRow(rowIndex);

    if (rowIndex == 0) {
      row.setHeight((short) (35 * 20));
    }
    for (int i = 0, lg = getColumnCount(); i < lg; i++) {
      Cell cell = row.createCell(i);
      cell.setCellValue(data.get(i));
      if (rowIndex == 0) {
        cell.setCellStyle(styleToolkit.getHeadStyle());
      } else if (rowIndex % 2 == 0) {
        // cell.setCellStyle(styleToolkit.getHeadStyle());
      } else {
        // cell.setCellStyle(styleToolkit.getHeadStyle());
      }
    }
  }

}
