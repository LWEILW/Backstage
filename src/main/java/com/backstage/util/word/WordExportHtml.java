package com.backstage.util.word;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.Map;

/**
 * html 导出 word 工具类
 *
 * @author zhangxiang
 */

public class WordExportHtml {

    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String title, Map<String, Object> params) {
        try {

            //word内容
            String content =
                    "<html>\n" +
                            "<head>\n" +
                            "<style type=\"text/css\">\n" +
                            "\n" +
                            ".title-name{\n" +
                            "            font-size: 21px;\n" +
                            "            font-family:\"宋体\";\n" +
                            "            font-weight: bold;\n" +
                            "            text-align: center;\n" +
                            "            line-height: 40px;\n" +
                            "            border: 1px solid;\n" +
                            "        }\n" +
                            "\n" +
                            "        .sub_title {\n" +
                            "            text-indent: 1em;\n" +
                            "            line-height: 2.5em;\n" +
                            "            font-size: 14px;\n" +
                            "            font-family: \"宋体\";\n" +
                            "            font-weight: bold;\n" +
                            "            text-align: left;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            border-left: 1px solid;\n" +
                            "            border-right: 1px solid #000;\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-wrap {\n" +
                            "            width: 100%;\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-normal {\n" +
                            "            width: 100%;\n" +
                            "            border-collapse: collapse;\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-normal .th   {\n" +
                            "            padding: 10px 0;\n" +
                            "            line-height: 23px;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            border-right: 1px solid;\n" +
                            "            text-align: center;\n" +
                            "            font-size: 14px;\n" +
                            "            font-family: \"宋体\";\n" +
                            "            font-weight: bold;\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-normal .td{\n" +
                            "            padding: 10px 0;\n" +
                            "            line-height: 23px;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            border-right: 1px solid;\n" +
                            "            text-align: center;\n" +
                            "            font-size: 14px;\n" +
                            "            font-family: \"宋体\";\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-normal .tdSpecial {\n" +
                            "            padding: 10px 0;\n" +
                            "            line-height: 23px;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            border-right: 1px solid;\n" +
                            "        }\n" +
                            "\n" +
                            "        @page {\n" +
                            "            mso-page-border-surround-header:no;\n" +
                            "            mso-page-border-surround-footer:no;\n" +
                            "        }\n" +
                            "\n" +
                            "        @page print-section1 {\n" +
                            "            size:800.9pt 500.3pt; //A4纸张的大小\n" +
                            "            mso-page-orientation:landscape;\n" +
                            "            margin:89.85pt 72.0pt 89.85pt 72.0pt;\n" +
                            "            mso-header-margin:42.55pt;//页眉\n" +
                            "            mso-footer-margin:49.6pt;//页脚\n" +
                            "            mso-paper-source:0;\n" +
                            "            layout-grid:15.6pt;\n" +
                            "        }\n" +
                            "\n" +
                            "        .print-section1 {\n" +
                            "           page:print-section1;\n" +
                            "        }\n" +
                            "\n" +
                            "</style>" +
                            "</head>" +
                            "<body>" +
                            "<div class=\"print-section1\">\n" +
                            "    <div>\n" +
                            "        <div style=\"width:100%\">\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"title-name\" colspan=\"9\">\n" + params.get("dailyName") + "</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"sub_title\" colspan=\"9\"> 更新时间：" + params.get("updateDate") + "</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"sub_title\" colspan=\"9\">一、客运量情况(人次)</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\" width=\"60\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"120\">日期</th>\n" +
                            "                        <th class=\"th\">客运量</th>\n" +
                            "                        <th class=\"th\">本月止客运量</th>\n" +
                            "                        <th class=\"th\">本月止日均客运量</th>\n" +
                            "                        <th class=\"th\">本年止累计客运量</th>\n" +
                            "                        <th class=\"th\">本年止日均客运量</th>\n" +
                            "                        <th class=\"th\">开通至今累计客运量</th>\n" +
                            "                        <th class=\"th\">开通至今日均客运量</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\" width=\"60\">" + params.get("dailySiteName") + "</td>\n" +
                            "                        <td class=\"td\" width=\"120\">" + params.get("dailyDate") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("passengerVolumeToday") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("passengerVolumeToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("passengerVolumeToMonthAverage") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("passengerVolumeToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("passengerVolumeToYearAverage") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("passengerVolumeToHither") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("passengerVolumeToHitherAverage") + "</td>\n" +
                            "                    </tr>\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "\n" +
                            "    <div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th  class=\"sub_title\"  colspan=\"13\">二、客车开行情况</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\"  width=\"60\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"120\">日期</th>\n" +
                            "                        <th class=\"th\">计划开行(列次)</th>\n" +
                            "                        <th class=\"th\">抽线列次</th>\n" +
                            "                        <th class=\"th\">加开列次</th>\n" +
                            "                        <th class=\"th\">实际开行(列次)</th>\n" +
                            "                        <th class=\"th\">晚点列次</th>\n" +
                            "                        <th class=\"th\">正点率(％)</th>\n" +
                            "                        <th class=\"th\">兑现率(％)</th>\n" +
                            "                        <th class=\"th\">客车总行驶里程(列公里)</th>\n" +
                            "                        <th class=\"th\">运营里程(列公里)</th>\n" +
                            "                        <th class=\"th\">1.正线调试(列公里)</th>\n" +
                            "                        <th class=\"th\">2.车辆段调试(列公里)</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"60\" rowspan=\"3\">" + params.get("dailySiteName") + "</td>\n" +
                            "                        <td class=\"td\" width=\"120\">" + params.get("dailyDate") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("plannedOperation") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineExtraction") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("addingColumns") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("actualOperation") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("listLater") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("punctualityRate") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("cashRate") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("operatingMileage") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("mainLineDebugging") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("depotCommissioning") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("totalMileage") + "</td>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"120\">本月</td>\n" +
                            "                        <td class=\"td\">" + params.get("plannedOperationToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineExtractionToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("addingColumnsToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("actualOperationToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("listLaterToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("punctualityRateToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("cashRateToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("operatingMileageToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("mainLineDebuggingToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("depotCommissioningToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("totalMileageToMonth") + "</td>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"120\">本年</td>\n" +
                            "                        <td class=\"td\">" + params.get("plannedOperationToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineExtractionToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("addingColumnsToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("actualOperationToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("listLaterToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("punctualityRateToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("cashRateToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("operatingMileageToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("mainLineDebuggingToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("depotCommissioningToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("totalMileageToYear") + "</td>\n" +
                            "                    </tr>\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "\n" +
                            "    <div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th  class=\"sub_title\" colspan=\"8\" >三、时刻表情况</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\"  width=\"60\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"120\">日期</th>\n" +
                            "                        <th class=\"th\">运营时刻表编号</th>\n" +
                            "                        <th class=\"th\">最小行车间隔(秒)</th>\n" +
                            "                        <th class=\"th\">运营列车数</th>\n" +
                            "                        <th class=\"th\">备用列车数（正线）</th>\n" +
                            "                        <th class=\"th\">备用列车数（段/场）</th>\n" +
                            "                        <th class=\"th\">高峰期上线列车(列)</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"60\">" + params.get("dailySiteName") + "</td>\n" +
                            "                        <td class=\"td\" width=\"120\">" + params.get("dailyDate") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("timetableNum") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("minRowsPacing") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("trainNumber") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("spareTrainMain") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("spareTrainLine") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("peakTrain") + "</td>\n" +
                            "                    </tr>\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "\n" +
                            "    <div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th  class=\"sub_title\" colspan=\"13\" >四、安全及列车服务情况</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\"  width=\"60\" rowspan=\"2\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"120\" rowspan=\"2\">日期</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">安全运营天数</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">加开列次</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">接待列次</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">救援列次</th>\n" +
                            "                        <th class=\"th\" colspan=\"2\">清客</th>\n" +
                            "                        <th class=\"th\" colspan=\"2\">晚点</th>\n" +
                            "                        <th class=\"th\" colspan=\"2\">抽线</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">下线列次</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\">故障</th>\n" +
                            "                        <th class=\"th\">其他</th>\n" +
                            "                        <th class=\"th\">故障</th>\n" +
                            "                        <th class=\"th\">其他</th>\n" +
                            "                        <th class=\"th\">故障</th>\n" +
                            "                        <th class=\"th\">其他</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"60\" rowspan=\"3\">" + params.get("dailySiteName") + "</td>\n" +
                            "                        <td class=\"td\" width=\"120\">" + params.get("dailyDate") + "</td>\n" +
                            "                        <td class=\"td\" rowspan=\"3\">" + params.get("safeOperationDays") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("addingColumns") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("receptionList") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("rescueList") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("qingkeFault") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("qingkeOther") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("laterFault") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("laterOther") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("pumpingFault") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("pumpingOther") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("offlineRank") + "</td>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"120\">本月</td>\n" +
                            "                        <td class=\"td\">" + params.get("addingColumnsToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("receptionListToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("rescueListToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("qingkeFaultToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("qingkeOtherToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("laterFaultToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("laterOtherToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("pumpingFaultToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("pumpingOtherToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("offlineRankToMonth") + "</td>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\" width=\"120\">本年</td>\n" +
                            "                        <td class=\"td\">" + params.get("addingColumnsToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("receptionListToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("rescueListToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("qingkeFaultToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("qingkeOtherToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("laterFaultToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("laterOtherToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("pumpingFaultToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("pumpingOtherToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("offlineRankToYear") + "</td>\n" +
                            "                    </tr>\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "\n" +
                            "    <div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th  class=\"sub_title\" colspan=\"10\" >五、电能消耗情况(千瓦时)</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\"  width=\"60\" rowspan=\"2\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"120\" rowspan=\"2\">时间</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">牵引能耗</th>\n" +
                            "                        <th class=\"th\" colspan=\"2\">动力、照明能耗</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">线损及其他</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">总能耗（来源主所）</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">牵引/总(％)</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">动力、照明/总(％)</th>\n" +
                            "                        <th class=\"th\" rowspan=\"2\">线损及其他/总(％)</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\">正线、车辆段</th>\n" +
                            "                        <th class=\"th\">主所</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"60\" rowspan=\"3\">" + params.get("dailySiteName") + "</td>\n" +
                            "                        <td class=\"td\" width=\"120\">" + params.get("dailyDate") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("tractionConsumption") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineDepot") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("masterOffice") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineLossOthers") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("totalConsumption") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("tractionRatio") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("poweredLightingRatio") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineOthersRatio") + "</td>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"120\">本月</td>\n" +
                            "                        <td class=\"td\">" + params.get("tractionConsumptionToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineDepotToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("masterOfficeToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineLossOthersToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("totalConsumptionToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("tractionRatioToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("poweredLightingRatioToMonth") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineOthersRatioToMonth") + "</td>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  width=\"120\">本年</td>\n" +
                            "                        <td class=\"td\">" + params.get("tractionConsumptionToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineDepotToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("masterOfficeToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineLossOthersToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("totalConsumptionToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("tractionRatioToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("poweredLightingRatioToYear") + "</td>\n" +
                            "                        <td class=\"td\">" + params.get("lineOthersRatioToYear") + "</td>\n" +
                            "                    </tr>\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "\n" +
                            "    <div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"sub_title\" colspan=\"5\" >六、正线动车作业情况</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\"  width=\"60\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"120\">日期</th>\n" +
                            "                        <th class=\"th\">作业代码</th>\n" +
                            "                        <th class=\"th\">作业内容</th>\n" +
                            "                        <th class=\"th\">作业区域</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  colspan=\"5\">暂无数据</td>" +
                            "                    </tr>\n" +
                            "\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "\n" +
                            "    <div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"sub_title\"  style=\"border-bottom:1px solid;\" colspan=\"6\" >七、运营信息</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"sub_title\" colspan=\"6\" >1、运营信息类</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\"  width=\"40\">序号</th>\n" +
                            "                        <th class=\"th\" width=\"60\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"100\">接报日期</th>\n" +
                            "                        <th class=\"th\" width=\"80\">接报时间</th>\n" +
                            "                        <th class=\"th\" width=\"100\">事件影响</th>\n" +
                            "                        <th class=\"th\">事件详情</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  colspan=\"6\">暂无数据</td>" +
                            "                    </tr>\n" +
                            "\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th  class=\"sub_title\" colspan=\"7\" >2、故障类</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\" width=\"40\">序号</th>\n" +
                            "                        <th class=\"th\" width=\"60\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"100\">接报日期</th>\n" +
                            "                        <th class=\"th\" width=\"100\">接报时间</th>\n" +
                            "                        <th class=\"th\" width=\"100\">专业</th>\n" +
                            "                        <th class=\"th\" width=\"100\">事件影响</th>\n" +
                            "                        <th class=\"th\" >事件详情</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\"  colspan=\"7\">暂无数据</td>" +
                            "                    </tr>\n" +
                            "\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "        <div>\n" +
                            "            <div class=\"table-wrap\">\n" +
                            "                <table class=\"table-normal\">\n" +
                            "                    <tr>\n" +
                            "                        <th  class=\"sub_title\" colspan=\"5\" >3、其他信息</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>\n" +
                            "                        <th class=\"th\" style=\"border-left: 1px solid;\" width=\"40\">序号</th>\n" +
                            "                        <th class=\"th\" width=\"60\">线路</th>\n" +
                            "                        <th class=\"th\" width=\"100\">接报日期</th>\n" +
                            "                        <th class=\"th\" width=\"80\">接报时间</th>\n" +
                            "                        <th class=\"th\" >事件详情</th>\n" +
                            "                    </tr>\n" +
                            "                    <tr>" +
                            "                        <td class=\"td\" style=\"border-left: 1px solid;\" colspan=\"5\">暂无数据</td>" +
                            "                    </tr>\n" +
                            "\n" +
                            "                            </table>\n" +
                            "                        </td>\n" +
                            "                    </tr>\n" +
                            "                </table>\n" +
                            "            </div>\n" +
                            "        </div>\n" +
                            "    </div>\n" +
                            "</div>" +
                            "</body>" +
                            "</html>";

            //这里是必须要设置编码的，不然导出中文就会乱码。
            byte b[] = content.getBytes("GBK");
            //将字节数组包装到流中
            ByteArrayInputStream bais = new ByteArrayInputStream(b);

            /*
             * 关键地方
             * 生成word格式 */
            POIFSFileSystem poifs = new POIFSFileSystem();
            DirectoryEntry directory = poifs.getRoot();
            DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);
            //输出文件
            request.setCharacterEncoding("utf-8");
            //导出word格式
            response.setContentType("application/msword");
            response.addHeader("Content-Disposition", "attachment;filename=" +
                    new String(title.getBytes("GB2312"), "iso8859-1") + ".doc");
            ServletOutputStream ostream = response.getOutputStream();
            poifs.writeFilesystem(ostream);
            bais.close();
            ostream.close();
            poifs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
