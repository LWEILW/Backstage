package com.backstage.util;


import com.backstage.entity.admin.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Excel导出
 */
@Component
public class ExcelUtil {

    private final static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    @Value("${fdfs.storage.path}")
    private String completionExcelStoragePath;

    @Value("${fdfs.access.url}")
    private String completionExcelAccessUrl = "C:/Users/DELL/Desktop";

    private final static String fileName = "用户上载模板.xlsx";

    public String export(List<User> userList) throws IOException, ParseException {
        Workbook wb;

        //旧Excel—模板
        String filePath = getFullFilePath(completionExcelAccessUrl, fileName);
        String targetFileName;

        // 创建模板工作表
        if (filePath.indexOf(".xlsx") > -1) {
            targetFileName = getFullFilePath(completionExcelAccessUrl, generateFileName("xlsx"));
            copyFile(filePath, targetFileName);
            wb = new XSSFWorkbook(new FileInputStream(targetFileName));
        } else {
            targetFileName = getFullFilePath(completionExcelAccessUrl, generateFileName("xls"));
            copyFile(filePath, targetFileName);
            wb = new HSSFWorkbook(new FileInputStream(targetFileName));
        }

        CellStyle styleBOLD = createBorderedStyle(wb);


        Sheet sheet = wb.getSheet("sheet1");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//        Calendar c = Calendar.getInstance();
//        c.setTime(sdf.parse(date));
//
//        int year = c.get(Calendar.YEAR);
//
//        int month = sdf.parse(date).getMonth() + 1;
//        int day = sdf.parse(date).getDate();
//
//        if (month == 1) {
//            sheet.getRow(0).getCell(3).setCellValue((year - 1) + "年制品品保检查");
//            sheet.getRow(1).getCell(3).setCellValue("12月直行率");
//        } else {
//            sheet.getRow(0).getCell(3).setCellValue(year + "年制品品保检查");
//            sheet.getRow(1).getCell(3).setCellValue(month - 1 + "月直行率");
//        }
//
//        sheet.getRow(0).getCell(6).setCellValue(month + "月" + day + "日品保分析");
//        sheet.getRow(0).getCell(17).setCellValue(year + "年" + month + "月度累计制品直行率");

        for (int i = 0; userList.size() > i; i++) {
            Row row = sheet.createRow(i);



            // 用户ID
            int userId = userList.get(i).getUserId();
            Cell cell1 = row.getCell(6);
            if (null == cell1) {
                cell1 = row.createCell(6);
            }
            cell1.setCellValue(userId);// 用户名

            // 用户名称
            String userName = userList.get(i).getUserName();
            Cell cell2 = row.getCell(6);
            if (null == cell2) {
                cell2 = row.createCell(6);
            }
            cell2.setCellValue(userName);// 用户名

//            sheet.getRow(i).getCell(5).setCellValue(userId);
//            sheet.getRow(2).getCell(5).setCellValue(userId);
//            sheet.getRow(3).getCell(5).setCellValue(1);
//            sheet.getRow(4).getCell(5).setCellValue(2);
//            sheet.getRow(5).getCell(5).setCellValue(3);
//            sheet.getRow(6).getCell(6).setCellValue("2324234");
        }


        FileOutputStream fOut = new FileOutputStream(targetFileName);
        wb.write(fOut);
        wb.close();
        return targetFileName;
    }

    private CellStyle createBorderedStyle(Workbook wb) {
        CellStyle style = wb.createCellStyle();
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        return style;
    }

    private String generateFileName(String suffix) {
        String filename;
        File file;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        String base = format.format(date);
        filename = base + "." + suffix;
        file = new File(filename);
        int i = 1;
        while (file.exists()) {
            filename = String.format("%s_%d.%s", base, i, suffix);
            i++;
        }
        return filename;
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    private boolean copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { // 文件存在时
                logger.debug("oldPath:" + oldPath);
                logger.debug("newPath:" + newPath);
                InputStream inStream = new FileInputStream(oldPath); // 读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; // 字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            } else {
                logger.debug("oldPath:!!!!!!!!!!!!!!!!!!!!!!!!");
            }
            return true;
        } catch (Exception e) {
            logger.debug("Exception:" + e.getMessage());
            return false;
        }
    }

    /**
     * 根据路径和文件名称合并为全路径
     *
     * @param path
     * @param name
     * @return
     */
    private String getFullFilePath(String path, String name) {
        if (path.endsWith(File.separator)) {
            return path + name;
        } else {
            return path + File.separator + name;
        }
    }

}
