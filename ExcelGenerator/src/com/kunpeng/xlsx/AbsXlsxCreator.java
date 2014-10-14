package com.kunpeng.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kunpeng.base.MCategory;
import com.kunpeng.base.MData;
import com.kunpeng.io.JsonFileReader;
import com.kunpeng.model.WorkBookInfo;

public abstract class AbsXlsxCreator {

  protected JsonFileReader reader = null;
  protected Gson gson = null;

  public AbsXlsxCreator(JsonFileReader reader, Gson gson) {
    this.reader = reader;
    this.gson = gson;
  }

  /**
   * 
   * @param sh
   * @param info
   * @param files
   * @param categoryIndex
   * @throws IOException
   * @throws JsonSyntaxException
   */
  protected void createSheetContent(Sheet sh, WorkBookInfo info, File[] files, int categoryIndex)
      throws IOException, JsonSyntaxException {
    Row header = sh.createRow(0);
    List<String> headerContent = info.getCategoryContentHeaders(categoryIndex);
    int columnCount = headerContent.size();
    for (int i = 0; i < columnCount; i++) {
      Cell cell = header.createCell(i);
      cell.setCellValue(headerContent.get(i));
    }
    // 生成表内数据(每一个工作表都重新io所有文件,以少量时间换取海量数据支持)
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      String json = reader.read(file);
      int firstL = json.indexOf("{");
      String label = json.substring(0, firstL);
      String rJson = json.substring(firstL);
      MData data = gson.fromJson(rJson, MData.class);
      Row row = sh.createRow(i + 1);
      Cell labelCell = row.createCell(0);
      labelCell.setCellValue(label);
      MCategory category = data.getCategories().get(categoryIndex);
      for (int j = 1; j < headerContent.size(); j++) {
        Cell cell = row.createCell(j);
        cell.setCellValue(category.getContent(headerContent.get(j)));
        System.out.print("N - " + headerContent.get(j));
        System.out.println("; V - " + category.getContent(headerContent.get(j)));
      }
    }
  }

  protected void createFile(SXSSFWorkbook workbook, String filePath) throws IOException {
    FileOutputStream out = new FileOutputStream(filePath);
    workbook.write(out);
    workbook.dispose();
  }

  public abstract int create();

}
