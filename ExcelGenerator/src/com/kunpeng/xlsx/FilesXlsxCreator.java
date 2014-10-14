package com.kunpeng.xlsx;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kunpeng.io.JsonFileReader;
import com.kunpeng.model.WorkBookInfo;

public final class FilesXlsxCreator extends AbsXlsxCreator {

  private WorkBookInfo info = null;
  private File[] files = null;
  private String genFilePath = null;

  public FilesXlsxCreator(JsonFileReader reader, Gson gson, WorkBookInfo info, File[] files,
      String genFilePath) {
    super(reader, gson);
    this.info = info;
    this.files = files;
    this.genFilePath = genFilePath;
  }

  @Override
  public int create() {
    List<String> fileNames = info.getCategoriesNames();
    for (String fileName : fileNames) {
      SXSSFWorkbook workbook = new SXSSFWorkbook(175);
      Sheet sh = workbook.createSheet("Default");
      try {
        createSheetContent(sh, info, files, fileNames.indexOf(fileName));
        createFile(workbook, genFilePath + "/" + fileName + ".xlsx");
      } catch (JsonSyntaxException e) {
        return -1;
      } catch (IOException e) {
        return -2;
      }
    }
    return 0;
  }

}
