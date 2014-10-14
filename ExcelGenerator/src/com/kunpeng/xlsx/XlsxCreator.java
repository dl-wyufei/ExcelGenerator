package com.kunpeng.xlsx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

  private JsonFileReader reader = new JsonFileReader();
  private Gson gson = new Gson();
  private File[] files = null;
  private WorkBookInfo info = null;
  private int genKind = XLSX_SEP_SHEET;
  private String genPath = "D:/DefaultFile.xlsx";

  public XlsxCreator(String sourcePath, String genPath, int genKind) {
    this.genKind = genKind;
    this.genPath = genPath;
    try {
      this.files = reader.getFiles(new File(sourcePath));
      info = initWorkBookInfo(files);
    } catch (JsonSyntaxException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void create() {
    switch (genKind) {
      case XLSX_ALL_IN_ONE:
        (new OneXlsxCreator(reader, gson, info, files, genPath)).create();
        break;
      case XLSX_SEP_SHEET:
        (new SheetsXlsxCreator(reader, gson, info, files, genPath)).create();
        break;
      case XLSX_SEP_FILE:
        (new FilesXlsxCreator(reader, gson, info, files, genPath)).create();
        break;
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
          categoryContentNames.add("Label");
          info.addCategoryContentHeaders(categoryContentNames);
        }
        Utils.addDistinctElement(categoryContentNames, category.getKeys());
      }
    }
    return info;
  }

}
