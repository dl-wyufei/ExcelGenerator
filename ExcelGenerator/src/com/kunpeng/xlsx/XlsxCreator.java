package com.kunpeng.xlsx;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kunpeng.Utils;
import com.kunpeng.base.MCategory;
import com.kunpeng.base.MData;
import com.kunpeng.io.JsonFileReader;
import com.kunpeng.model.WorkBookInfo;

public final class XlsxCreator {

  /** 所有数据集中于一个文件一个工作表 */
  public static final int XLSX_ALL_IN_ONE = 0;
  /** 数据存于一个文件不同工作表 */
  public static final int XLSX_SEP_SHEET = 1;
  /** 数据存于不同文件 */
  public static final int XLSX_SEP_FILE = 2;

  private Gson gson = new Gson();

  private JsonFileReader reader = new JsonFileReader();

  private File[] files = null;

  private WorkBookInfo workBookInfo = null;

  public XlsxCreator(File[] files) {
    this.files = files;
    try {
      workBookInfo = initWorkBookInfo(files);
    } catch (JsonSyntaxException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void create(int genKind) {
    try {
      switch (genKind) {
        case XLSX_ALL_IN_ONE:
          break;
        case XLSX_SEP_SHEET:
          createWorkBookWithSeprateSheet();
          break;
        case XLSX_SEP_FILE:
          break;
      }
    } catch (IOException e) {

    } catch (JsonSyntaxException e) {

    }
  }

  /** 创建文件 - 数据存于一个文件不同工作表 */
  private void createWorkBookWithSeprateSheet() throws IOException, JsonSyntaxException {
    SXSSFWorkbook workbook = new SXSSFWorkbook(175);
    List<String> sheetNames = workBookInfo.getCategoriesNames();
    for (String sheetName : sheetNames) {
      Sheet sh = workbook.createSheet(sheetName);
      // 生成表头
      Row header = sh.createRow(0);
      int index = sheetNames.indexOf(sheetName);
      List<String> headerContent = workBookInfo.getCategoryContentHeaders(index);
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
        MCategory category = data.getCategories().get(index);
        List<String> names = category.getNames();
        for (int j = 0; j < names.size(); j++) {
          Cell cell = row.createCell(j + 1);
          cell.setCellValue(category.getContent(names.get(j)));
        }
      }
    }
    try {
      FileOutputStream out = new FileOutputStream("D:/hahaha.xlsx");
      workbook.write(out);
      workbook.dispose();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** 初始化相关信息留作后用 */
  private WorkBookInfo initWorkBookInfo(File[] files) throws IOException, JsonSyntaxException {
    WorkBookInfo info = new WorkBookInfo();

    for (File file : files) {
      String json = reader.read(file);
      int firstL = json.indexOf("{");
      String rJson = json.substring(firstL);
      MData data = gson.fromJson(rJson, MData.class);
      List<MCategory> categories = data.getCategories();

      if (info.getCategoriesNames().size() == 0) {
        for (MCategory category : categories) {
          info.addCategoryName(category.getName());
        }
      }

      for (int i = 0, lg = categories.size(); i < lg; i++) {
        MCategory category = categories.get(i);
        List<String> categoryContentNames = null;
        try {
          categoryContentNames = info.getCategoryContentHeaders(i);
        } catch (IndexOutOfBoundsException e) {
          categoryContentNames = new ArrayList<String>();
          info.addCategoryContentHeaders(categoryContentNames);
        }
        categoryContentNames.add("Label");
        Utils.addDistinctElement(categoryContentNames, category.getNames());
      }
    }
    return info;
  }

}
