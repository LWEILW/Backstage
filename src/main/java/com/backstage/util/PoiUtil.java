package com.backstage.util;

import com.backstage.entity.admin.UserPoi;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jboss.logging.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zhu jiabing
 */
public class PoiUtil {

    private static Logger logger = Logger.getLogger(PoiUtil.class);
    private final static String XLS = "xls";
    private final static String XLSX = "xlsx";

    /**
     * 获得Excel
     */
    public static Workbook getWorkbook(MultipartFile file) throws IOException {
        // 检查文件
        checkFile(file);
        // 获得Workbook工作薄对象
        return getWorkBook(file);
    }

    public static void checkFile(MultipartFile file) throws IOException {
        // 判断文件是否存在
        if (null == file) {
            logger.error("文件不存在！");
            throw new FileNotFoundException("文件不存在！");
        }
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 判断文件是否是excel文件
        if (!fileName.endsWith(XLS) && !fileName.endsWith(XLSX)) {
            logger.error(fileName + "不是excel文件");
            throw new IOException(fileName + "不是excel文件");
        }
    }

    public static Workbook getWorkBook(MultipartFile file) {
        // 获得文件名
        String fileName = file.getOriginalFilename();
        // 创建Workbook工作薄对象，表示整个excel
        Workbook workbook = null;
        try {
            // 获取excel文件的io流
            InputStream is = file.getInputStream();
            // 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if (fileName.endsWith(XLS)) {
                // 2003
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith(XLSX)) {
                // 2007
                workbook = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        return workbook;
    }

    public static String getCellValue(Cell cell) {
        String cellValue = "";
        // 以下是判断数据的类型
        switch (cell.getCellTypeEnum()) {
            // 数字
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                } else {
                    DataFormatter dataFormatter = new DataFormatter();
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            // 字符串
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            // Boolean
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue() + "";
                break;
            // 公式
            case FORMULA:
                cellValue = cell.getCellFormula() + "";
                break;
            // 空值
            case BLANK:
                cellValue = "";
                break;
            // 故障
            case ERROR:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }


    /**
     * 电能消耗批量上载
     */
    public static List<UserPoi> powerConsumptionReadExcel(Workbook workbook) {
        // 创建返回对象，把每行中的值作为一个数组，所有行作为一个集合返回
        List<UserPoi> list = new ArrayList<>();
        if (workbook != null) {
            // 获得当前sheet工作表
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return null;
            }
            // 获得当前sheet的开始行
            int firstRowNum = sheet.getFirstRowNum();
            // 获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();


            String dailyDate = sheet.getRow(firstRowNum + 2).getCell(0).getStringCellValue();
//            String dailySiteName = sheet.getRow(firstRowNum + 2).getCell(2).getStringCellValue();


            // 循环除了前两行的所有行
            for (int rowNum = firstRowNum + 2; rowNum <= lastRowNum; rowNum++) {
                // 获得当前行
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                UserPoi powerConsumptionPoi = new UserPoi();

                // dailyDate 日期
                try {
                    if (!"".equals(getCellValue(row.getCell(0)))) {
                        powerConsumptionPoi.setUserAccount(getCellValue(row.getCell(0)));
                    } else {
                        powerConsumptionPoi.setErrorCode(1);
                        list.add(powerConsumptionPoi);
                        continue;
                    }
                } catch (Exception e) {
                    powerConsumptionPoi.setErrorCode(1);
                    list.add(powerConsumptionPoi);
                    continue;
                }


                // nzzPower  南庄动力电量
                try {
                    if (!"".equals(getCellValue(row.getCell(1)))) {
                        powerConsumptionPoi.setUserAccount(getCellValue(row.getCell(1)));
                    } else {
                        powerConsumptionPoi.setErrorCode(2);
                        list.add(powerConsumptionPoi);
                        continue;
                    }
                } catch (Exception e) {
                    powerConsumptionPoi.setErrorCode(2);
                    list.add(powerConsumptionPoi);
                    continue;
                }

                list.add(powerConsumptionPoi);

            }
        }
        return list;
    }





}
