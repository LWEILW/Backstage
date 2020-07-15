package com.backstage.util;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;

/**
 * html 导出 word 工具类
 *
 * @author zhangxiang
 */

public class WordUtil {


    public static void exportWord(HttpServletRequest request, HttpServletResponse response, String title, String text) {

        try {
            //word内容
            String content =
                    "<html>" +
                            "<head>    " +
                            "<style type=\"text/css\">\n" +
                            "\n" +
                            "      .title-name{\n" +
                            "            font-size: 16px;\n" +
                            "            font-weight: bold;\n" +
                            "            text-align: center;\n" +
                            "            line-height: 30px;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            background-color: #acd1f7;\n" +
                            "            text-align: center;\n" +
                            "            border-right: 1px solid;\n" +
                            "        }" +
                            "\n" +
                            "        .title {\n" +
                            "            font-size: 16px;\n" +
                            "            font-weight: bold;\n" +
                            "            text-align: center;\n" +
                            "            line-height: 30px;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "        }\n" +
                            "\n" +
                            "        .line-title {\n" +
                            "            background-color: #acd1f7;\n" +
                            "            text-align: center;\n" +
                            "            border-right: 1px solid;\n" +
                            "        }\n" +
                            "\n" +
                            "        .sub_title {\n" +
                            "            background-color: #dcdfe6;\n" +
                            "            text-indent: 1em;\n" +
                            "            line-height: 2em;\n" +
                            "            border-left: 1px solid;\n" +
                            "            border-right: 1px solid #000;\n" +
                            "        }\n" +
                            "\n" +
                            "        .sub_title .line {\n" +
                            "            text-align: center;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            overflow: hidden;\n" +
                            "            text-overflow: ellipsis;\n" +
                            "            white-space: nowrap;\n" +
                            "        }\n" +
                            "\n" +
                            "        .line-clear-border {\n" +
                            "            border-right: none;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            text-indent: 1em;\n" +
                            "        }\n" +
                            "\n" +
                            "        .line-input {\n" +
                            "            background-color: #dcdfe6;\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-wrap {\n" +
                            "            border-top: 1px solid;\n" +
                            "            border-left: 1px solid;\n" +
                            "        }\n" +
                            "        .table-normal {\n" +
                            "            width: 100%;\n" +
                            "            border-collapse: collapse;\n" +
                            "            text-align: center;\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-normal .th,  .table-normal .td {\n" +
                            "            padding: 10px 0;\n" +
                            "            line-height: 23px;\n" +
                            "            border-bottom: 1px solid;\n" +
                            "            border-right: 1px solid;\n" +
                            "        }\n" +
                            "\n" +
                            "        .table-normal .th {\n" +
                            "            font-weight: normal;\n" +
                            "            background-color: #acd1f7;\n" +
                            "        }\n" +
                            "\n" +
                            "    </style>" +
                            "</head>" +
                            "<body>" +
                            text +
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

