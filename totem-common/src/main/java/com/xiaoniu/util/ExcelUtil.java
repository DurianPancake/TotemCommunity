package com.xiaoniu.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.excel.export.ExcelExportService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @Author: LLH
 * @Date: 2019/6/22 15:14
 */
public abstract class ExcelUtil {

    /** 定义了sheet的大小 */
    private static int sheetSize = 1000;

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,
                                   String fileName, boolean isCreateHeader, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, title, sheetName, isCreateHeader);
    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName,
                                   HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName,
                                      HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null); downLoadExcel(fileName, response, workbook);
    }

    /**
     * @author LLH
     */
    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName,
                                      HttpServletResponse response, String title, String sheet, boolean isCreateHeader) {
        // 创建多sheet的workbook
        Workbook workbook = exportWorkbook(list, ExcelType.HSSF, title, sheet, pojoClass, isCreateHeader);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }


    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    /**
     * 自定义自己的workbook创建方法
     * @author LLH
     */
    private static Workbook exportWorkbook(List<?> list, ExcelType type, String title, String sheet,
                                           Class<?> pojoClass, boolean isCreateHeader) {
        Workbook workbook;
        if (ExcelType.HSSF.equals(type)) {
            workbook = new HSSFWorkbook();
        } else if (list.size() < 100000) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook();
        }
        int offset = 0;
        ExcelExportService exportService = new ExcelExportService();
        int count = 1;
        while (offset < list.size()) {
            int left = list.size() - offset;
            int nextPos = left < sheetSize? left : sheetSize;
            List<?> listPiece = list.subList(offset, offset + nextPos);
            ExportParams exportParams = new ExportParams(title, sheet + (count++));
            exportParams.setCreateHeadRows(isCreateHeader);
            exportService.createSheet(workbook, exportParams, pojoClass, listPiece);
        }
        return workbook;
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" +
                    URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            //throw new NormalException(e.getMessage());
        }
    }

    public static <T> List<T> importExcel(String filePath,Integer titleRows,Integer headerRows, Class<T> pojoClass){
        if (StringUtil.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (NoSuchElementException e){
            //throw new NormalException("模板不能为空");
        } catch (Exception e) {
            e.printStackTrace();
            //throw new NormalException(e.getMessage());
        } return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){ return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (NoSuchElementException e){
            // throw new NormalException("excel文件不能为空");
        } catch (Exception e) {
            //throw new NormalException(e.getMessage());
            System.out.println(e.getMessage());
        }
        return list;
    }

}
