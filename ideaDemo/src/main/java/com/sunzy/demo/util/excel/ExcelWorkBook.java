package com.sunzy.demo.util.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunzy
 * @date 2019/10/18
 * 整个excel文档
 */
public class ExcelWorkBook {
    //excel名称
    private String excelName;
    //sheet页面
    private List<ExcelSheet> excelSheets;

    public ExcelWorkBook() {
    }

    public ExcelWorkBook(String excelName) {
        this.excelName = excelName;
    }

    public ExcelWorkBook(String excelName, List<ExcelSheet> excelSheets) {
        this.excelName = excelName;
        this.excelSheets = excelSheets;
    }

    public String getExcelName() {
        return excelName;
    }

    public void setExcelName(String excelName) {
        this.excelName = excelName;
    }

    public List<ExcelSheet> getExcelSheets() {
        if(excelSheets.isEmpty()){
            excelSheets = new ArrayList<ExcelSheet>();
        }
        return excelSheets;
    }

    public void setExcelSheets(List<ExcelSheet> excelSheets) {
        this.excelSheets = excelSheets;
    }
}
