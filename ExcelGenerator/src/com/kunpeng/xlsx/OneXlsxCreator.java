package com.kunpeng.xlsx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

public final class OneXlsxCreator extends AbsXlsxCreator {

  private WorkBookInfo info = null;
  private File[] files = null;
  private String genFilePath = null;

  private HashMap<String, HeaderCollection> headerCollMap = new HashMap<String, HeaderCollection>();

  public OneXlsxCreator(JsonFileReader reader, Gson gson, WorkBookInfo info, File[] files,
      String genFilePath) {
    super(reader, gson);
    this.info = info;
    this.files = files;
    this.genFilePath = genFilePath;
  }

  @Override
  public int create() {
    SXSSFWorkbook workbook = new SXSSFWorkbook(175);
    Sheet sh = workbook.createSheet("Default");
    List<String> headerNames = getHeaderNames();
    // 创建表头
    Row header = sh.createRow(0);
    for (int i = 0, lg = headerNames.size(); i < lg; i++) {
      String headerName = headerNames.get(i);
      Cell hCell = header.createCell(i);
      hCell.setCellValue(headerName);
    }
    // 创建表内数据
    for (int i = 0; i < files.length; i++) {
      File file = files[i];
      String json = null;
      try {
        json = reader.read(file);
      } catch (IOException e) {
        return -2;
      }
      int firstL = json.indexOf("{");
      String label = json.substring(0, firstL);
      String rJson = json.substring(firstL);
      MData data = null;
      try {
        data = gson.fromJson(rJson, MData.class);
      } catch (JsonSyntaxException e) {
        return -1;
      }
      Row row = sh.createRow(i + 1);
      Cell labelCell = row.createCell(0);
      labelCell.setCellValue(label);
      for (int j = 1, cLg = headerNames.size(); j < cLg; j++) {
        HeaderCollection hc = headerCollMap.get(headerNames.get(j));
        if (hc == null) {
          return -3;
        }
        String cateName = hc.category;
        String detailName = hc.contentHeader;

        String value = "N/A";
        try {
          MCategory category =
              data.getCategories().get(info.getCategoriesNames().indexOf(cateName));
          value = category.getContent(detailName);
        } catch (Exception e) {
          return -3;
        }

        Cell cell = row.createCell(j);
        cell.setCellValue(value);
      }
    }
    try {
      createFile(workbook, genFilePath);
    } catch (IOException e) {
      return -2;
    }
    return 0;
  }

  private List<String> getHeaderNames() {
    List<String> headers = new ArrayList<String>();
    headers.add("Label");

    List<String> categoriesNames = info.getCategoriesNames();
    for (String categoryName : categoriesNames) {
      int index = categoriesNames.indexOf(categoryName);

      List<String> cateHeaders = info.getCategoryContentHeaders(index);
      for (int i = 1, lg = cateHeaders.size(); i < lg; i++) {
        String value = categoryName + "-" + cateHeaders.get(i);
        headers.add(value);
        headerCollMap.put(value, new HeaderCollection(categoryName, cateHeaders.get(i)));
      }
    }

    return headers;
  }

  private class HeaderCollection {

    HeaderCollection(String category, String contentHeader) {
      this.category = category;
      this.contentHeader = contentHeader;
    }

    String category;
    String contentHeader;
  }

}
