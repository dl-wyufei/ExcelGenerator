package com.kunpeng;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public final class StyleToolkit {

  private SXSSFWorkbook workBook = null;

  private XSSFCellStyle headStyle = null;
  private XSSFCellStyle dataStyle_0 = null;
  private XSSFCellStyle dataStyle_1 = null;

  public StyleToolkit(SXSSFWorkbook wb) {
    this.workBook = wb;
  }

  public CellStyle getHeadStyle() {
    if (headStyle == null) {
      headStyle = (XSSFCellStyle) workBook.createCellStyle();
      headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
      headStyle.setFillForegroundColor((new XSSFColor(new java.awt.Color(186, 186, 186))));
      headStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
      headStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
      headStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN); // 下边框
      headStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);// 左边框
      headStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);// 右边框
      Font font = workBook.createFont();
      font.setBoldweight(Font.BOLDWEIGHT_BOLD);
      font.setFontName("微软雅黑");
      font.setFontHeight((short) (13 * 20));
      headStyle.setFont(font);
    }
    return headStyle;
  }

  public CellStyle getDataStyle_0() {
    if (dataStyle_0 == null) {
      dataStyle_0 = (XSSFCellStyle) workBook.createCellStyle();
      dataStyle_0.setFillPattern(CellStyle.SOLID_FOREGROUND);
      dataStyle_0.setFillForegroundColor((new XSSFColor(new java.awt.Color(209, 233, 233))));
    }
    return dataStyle_0;
  }

  public CellStyle getDataStyle_1() {
    if (dataStyle_1 == null) {
      dataStyle_1 = (XSSFCellStyle) workBook.createCellStyle();
      dataStyle_1.setFillPattern(CellStyle.SOLID_FOREGROUND);
      dataStyle_1.setFillForegroundColor((new XSSFColor(new java.awt.Color(192, 225, 225))));
    }
    return dataStyle_1;
  }
}
