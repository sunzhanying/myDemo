package com.sunzy.demo.util;

import java.util.List;

/**
 * @author sunzy
 * @date 2019/10/18
 */
public class ExcelSheet {
    //sheet名称
    private String sheetName;
    //行数据，Object数组
    private List<Object[]> rowDatas;
    //行数据，字符串数组
    private List<String[]> rowStrings;

    public ExcelSheet() {
    }

    public ExcelSheet(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<Object[]> getRowDatas() {
        return rowDatas;
    }

    public void setRowDatas(List<Object[]> rowDatas) {
        this.rowDatas = rowDatas;
    }

    public List<String[]> getRowStrings() {
        return rowStrings;
    }

    public void setRowStrings(List<String[]> rowStrings) {
        this.rowStrings = rowStrings;
    }
}
