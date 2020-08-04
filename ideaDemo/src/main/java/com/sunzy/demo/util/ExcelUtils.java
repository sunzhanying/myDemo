package com.sunzy.demo.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import javax.servlet.ServletOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sunzy
 * @date 2019/10/18
 * 读取excel文件流，返回数组，list为每一行，String[] 为每一行中多少单元格。
 */
public class ExcelUtils {
    private static final String EXCEL03 = ".xls";
    private static final String EXCEL07 = ".xlsx";

    //读取excel文件流 返回文件中数据
    public static List<String[]> readStringExcel(InputStream inputStream,String fileName){
        List<String[]> list = new ArrayList<>();
        ExcelWorkBook workBook = readExcelWorkBook(inputStream,fileName);

        //如果存在
        if(workBook != null && workBook.getExcelSheets() != null && workBook.getExcelSheets().size() > 0){
            List<Object[]>  objects = workBook.getExcelSheets().get(0).getRowDatas();
            for(Object[] obj : objects){
                list.add(objectArrayToStringArray(obj));
            }
        }
        return list;
    }

    //导出Excel文件
    public static void exportExcelToResponse(String fileName, List<Object[]> rowDatas, String sheetName, ServletOutputStream outputStream){

        //创建book
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(fileName);
        //创建sheet
        List<ExcelSheet> excelSheets = new ArrayList<>();
        ExcelSheet sheet = new ExcelSheet(sheetName);
        sheet.setRowDatas(rowDatas);
        //把sheet添加到表中
        excelSheets.add(sheet);
        excelWorkBook.setExcelSheets(excelSheets);
        Workbook workbook = createExcelWorkBook(excelWorkBook);
        try{
            workbook.write(outputStream);
            if(outputStream != null){
                outputStream.close();
            }
        }catch (Exception e){

        }finally {

        }


    }

    private static Workbook createExcelWorkBook(ExcelWorkBook excelWorkBook) {
        Workbook workbook = null;
        //获取文件名
        String fileName = excelWorkBook.getExcelName();
        if(fileName != null && !"".equals(fileName)){
            if(suffixFileName(fileName).toLowerCase().equals(EXCEL03)){//03
                workbook = createHSSFWorkbook(excelWorkBook);
            }else if(suffixFileName(fileName).toLowerCase().equals(EXCEL07)){//07以及之后
                workbook = createXSSFWorkbook(excelWorkBook);
            }else {
                //LOG.error("excel格式不对，只能是xls,xlsx");
            }
        }
        return workbook;
    }

    private static Workbook createXSSFWorkbook(ExcelWorkBook excelWorkBook) {
        //创建文档
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        //sheet 数据
        List<ExcelSheet> excelSheets = excelWorkBook.getExcelSheets();
        if(excelSheets != null && excelSheets.size() > 0){
            //循环创建sheet
            for(ExcelSheet excelSheet:excelSheets){
                createSheet(workbook,excelSheet);
            }

        }else{//如果为空，创建一个空的
            createSheet(workbook,new ExcelSheet());
        }
        return workbook;
    }

    private static Workbook createHSSFWorkbook(ExcelWorkBook excelWorkBook) {
        //创建文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //sheet 数据
        List<ExcelSheet> excelSheets = excelWorkBook.getExcelSheets();
        if(excelSheets != null && excelSheets.size() > 0){
            //循环创建sheet
            for(ExcelSheet excelSheet:excelSheets){
                createSheet(workbook,excelSheet);
            }

        }else{//如果为空，创建一个空的
            createSheet(workbook,new ExcelSheet());
        }
        return workbook;
    }

    private static SXSSFSheet createSheet(SXSSFWorkbook workbook, ExcelSheet excelSheet) {
        //声明sheet对象
        SXSSFSheet sheet = null;
        //获取sheet名称
        String sheetName = excelSheet.getSheetName();
        if(sheetName != null && !"".equals(sheetName)){
            sheet = workbook.createSheet(sheetName);
        }else {
            sheet = workbook.createSheet();
        }
        //获得sheet的数据
        List<Object[]> rowDatas = excelSheet.getRowDatas();
        List<String[]> rowStrings = excelSheet.getRowStrings();
        //如果不为空，并有数据
        if(rowDatas != null && rowDatas.size() > 0){
            setCellData(sheet,rowDatas);
        }
        //如果为空，而且有数据
        if(rowStrings != null && rowStrings.size() > 0){
            setStringData(sheet,rowStrings);
        }
        return sheet;
    }

    private static HSSFSheet createSheet(HSSFWorkbook workbook, ExcelSheet excelSheet) {
        //声明sheet对象
        HSSFSheet sheet = null;
        //获取sheet名称
        String sheetName = excelSheet.getSheetName();
        if(sheetName != null && !"".equals(sheetName)){
            sheet = workbook.createSheet(sheetName);
        }else {
            sheet = workbook.createSheet();
        }
        //获得sheet的数据
        List<Object[]> rowDatas = excelSheet.getRowDatas();
        List<String[]> rowStrings = excelSheet.getRowStrings();
        //如果不为空，并有数据
        if(rowDatas != null && rowDatas.size() > 0){
            setCellData(sheet,rowDatas);
        }
        //如果为空，而且有数据
        if(rowStrings != null && rowStrings.size() > 0){
            setStringData(sheet,rowStrings);
        }
        return sheet;
    }

    //设置单元格的值
    private static void setStringData(HSSFSheet sheet, List<String[]> rowDatas) {
        //循环创建行和列
        for(int r = 0;r<rowDatas.size();r++){
            String[] data = rowDatas.get(r);
            //创建一行
            HSSFRow hssfRow = sheet.createRow(r);
            for(int c = 0;c<data.length;c++){
                //创建单元格
                HSSFCell cell = hssfRow.createCell(c);
                //获取值，然后对值进行类型判断
                String value = data[c];
                if(value != null && !"".equals(value)){
                    cell.setCellValue(value);
                }else {
                    cell.setCellValue(" ");
                }
            }
        }
    }

    //设置单元格的值
    private static void setStringData(SXSSFSheet sheet, List<String[]> rowDatas) {
        //循环创建行和列
        for(int r = 0;r<rowDatas.size();r++){
            String[] data = rowDatas.get(r);
            //创建一行
            SXSSFRow hssfRow = sheet.createRow(r);
            for(int c = 0;c<data.length;c++){
                //创建单元格
                SXSSFCell cell = hssfRow.createCell(c);
                //获取值，然后对值进行类型判断
                String value = data[c];
                if(value != null && !"".equals(value)){
                    cell.setCellValue(value);
                }else {
                    cell.setCellValue(" ");
                }
            }
        }
    }

    //设置单元格的值
    private static void setCellData(HSSFSheet sheet, List<Object[]> rowDatas) {
        //循环创建行和列
        for(int r = 0;r < rowDatas.size();r++){
            Object[] data = rowDatas.get(r);
            //创建一行
            HSSFRow hssfRow = sheet.createRow(r);
            for(int c = 0;c<data.length;c++){
                //创建单元格
                HSSFCell cell = hssfRow.createCell(c);
                //获取值，然后对值进行类型判断
                Object value = data[c];
                //调整宽度 todo
                 if(value instanceof  String){
                     cell.setCellValue(value.toString());
                 } else if(value instanceof Double){
                     cell.setCellValue((Double)value);
                 }else if(value instanceof  Integer){
                     cell.setCellValue((Integer)value);
                 }else if(value instanceof  Float){
                     cell.setCellValue((Float)value);
                 }else if(value instanceof  Boolean){
                     cell.setCellValue((Boolean)value);
                 }else if(value instanceof  Date | value instanceof java.sql.Date){
                     cell.setCellValue((Date)value);
                 }else {
                     cell.setCellValue(" ");
                 }

            }
        }
    }

    //设置单元格的值
    private static void setCellData(SXSSFSheet sheet, List<Object[]> rowDatas) {
        //循环创建行和列
        for(int r = 0;r < rowDatas.size();r++){
            Object[] data = rowDatas.get(r);
            //创建一行
            SXSSFRow hssfRow = sheet.createRow(r);
            for(int c = 0;c<data.length;c++){
                //创建单元格
                SXSSFCell cell = hssfRow.createCell(c);
                //获取值，然后对值进行类型判断
                Object value = data[c];
                //调整宽度 todo
                if(value instanceof  String){
                    cell.setCellValue(value.toString());
                } else if(value instanceof Double){
                    cell.setCellValue((Double)value);
                }else if(value instanceof  Integer){
                    cell.setCellValue((Integer)value);
                }else if(value instanceof  Float){
                    cell.setCellValue((Float)value);
                }else if(value instanceof  Boolean){
                    cell.setCellValue((Boolean)value);
                }else if(value instanceof  Date | value instanceof java.sql.Date){
                    cell.setCellValue((Date)value);
                }else {
                    cell.setCellValue(" ");
                }

            }
        }
    }

    private static String[] objectArrayToStringArray(Object[] obj) {
        String[] strings = new String[obj.length];
        for(int i = 0;i<obj.length;i++){
            strings[i] = toStringValue(obj[i]);
        }
        return strings;
    }

    public static String toStringValue(Object obj) {
        return (obj == null) ? "null" : obj.toString();
    }

    private static ExcelWorkBook readExcelWorkBook(InputStream inputStream, String fileName) {
        //声明对象
        ExcelWorkBook excelWorkBook = new ExcelWorkBook(fileName);
        if(fileName != null && !"".equals(fileName)){
            //2007版本之前，如2003版本excel，进行小写转换
            try{
                if(suffixFileName(fileName).toLowerCase().equals(EXCEL03)){//03
                    toExcelWorkBook(new HSSFWorkbook(inputStream),excelWorkBook);
                }else if(suffixFileName(fileName).toLowerCase().equals(EXCEL07)){//07以及之后
                    toExcelWorkBook(new XSSFWorkbook(inputStream),excelWorkBook);
                }else {
                    //LOG.error("excel格式不对，只能是xls,xlsx");
                }
            }catch (Exception e){

            }

        }
        return excelWorkBook;
    }

    //
    private static void toExcelWorkBook(Workbook wb, ExcelWorkBook excelWorkBook) {
        //获取有几个sheet页面
        int sheetNum = wb.getNumberOfSheets();
        //声明sheet列表对象
        List<ExcelSheet> excelSheets = new ArrayList<>();
        for(int index = 0;index < sheetNum;index ++){
            //获取第index个sheet
            Sheet sheet = wb.getSheetAt(index);
            //获取sheet名称
            ExcelSheet excelSheet = new ExcelSheet(sheet.getSheetName());
            //获取有多少行数据
            int totalRows = sheet.getPhysicalNumberOfRows();
            //声明每个sheet页的数据
            List<Object[]> rowDatas = new ArrayList<>();
            //声明最大的单元格，防止空值出现
            int rowCells = 0;
            for(int r = 0;r < totalRows;r++){//每一行数据读取
                //获取每一行
                Row row = sheet.getRow(r);
                if(row != null){
                    //获取每一行有效单元格
                    rowCells = Math.max(row.getLastCellNum(),rowCells);
                    Object[] data = new Object[rowCells];
                    for(int c = 0; c < rowCells; c++){
                        Cell cell = row.getCell(c);
                        //如果单元格不为空
                        if(cell != null){
                            data[c] = getCellValue(cell);
                        }
                    }
                    rowDatas.add(data);
                }
            }
            excelSheet.setRowDatas(rowDatas);
            excelSheets.add(excelSheet);
        }
        //设置sheet对象
        excelWorkBook.setExcelSheets(excelSheets);
    }

    //获取单元格的数据，将apache poi的Cell对象转成Object对象
    private static Object getCellValue(Cell cell) {
        Object cellValue = "";
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_STRING: //单元格如果是String类型
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_BOOLEAN: //单元格如果是Boolean类型
                cellValue = cell.getBooleanCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC: //单元格如果是数字类型
                if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
                    //读取日期格式
                    cellValue = dateToString(cell.getDateCellValue(),"yyyy-MM-dd");
                }else{
                    //读取数字
                    cellValue = cell.getNumericCellValue();
                    //如果是整形，需要去掉.0结尾
                    if(String.valueOf(cellValue).substring(String.valueOf(cellValue).indexOf(".")).equals(".0")){
                        if(cellValue != null){
                            cellValue = Integer.valueOf(toInt(cellValue));
                        }else if(String.valueOf(cellValue).matches("^[0-9].*]") && String.valueOf(cellValue).contains("E")){//数字开头并且包含E
                            DecimalFormat df = new DecimalFormat("#");
                            cellValue = df.format(cell.getNumericCellValue());
                        }
                    }
                }
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            case Cell.CELL_TYPE_ERROR:
                cellValue = cell.getErrorCellValue();
                break;
            case Cell.CELL_TYPE_BLANK:
                cellValue = " ";
                break;
        }
        return cellValue;
    }

    private static int toInt(Object object) {
        if(object instanceof Integer){
            return ((Integer)object).intValue();
        }else if(object instanceof Short){
            return ((Short)object).intValue();
        }else if(object instanceof Double){
            return ((Double)object).intValue();
        }else if(object instanceof Long){
            return ((Long)object).intValue();
        }else if(object instanceof Number){
            return ((Number)object).intValue();
        }else {
            return Integer.parseInt(object.toString());
        }
    }

    private static Object dateToString(Date dateCellValue, String s) {
        if(dateCellValue == null || s == null){
            return null;
        }
        return new SimpleDateFormat(s).format(dateCellValue);
    }

    private static String suffixFileName(String fileSuff){
        if(fileSuff != null && fileSuff.length() > 0){
            int i = fileSuff.lastIndexOf('.');
            if((i > -1) && (i < fileSuff.length())){
                return fileSuff.substring(i);
            }
        }
        return fileSuff;
    }
}
