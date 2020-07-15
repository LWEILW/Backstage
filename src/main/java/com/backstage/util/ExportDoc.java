package com.backstage.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ExportDoc {

    private Configuration configuration;
    private String encoding;

    public ExportDoc(String encoding) {
        this.encoding = encoding;
        configuration = new Configuration(Configuration.VERSION_2_3_22);
        configuration.setDefaultEncoding(encoding);

        //configuration.setClassForTemplateLoading(this.getClass(), "/templates/");
        try {
            //configuration.setDirectoryForTemplateLoading(new File("G:\\ideaWorkspace\\zwxtzhzx\\src\\main\\java\\com\\sx\\common\\wordexp\\templates"));
            //请将模板文件放在resource文件夹下的templates文件夹中
            configuration.setDirectoryForTemplateLoading(new File(this.getClass().getResource("/").getPath() + "templates"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Template getTemplate(String name) throws Exception {
        return configuration.getTemplate(name);

    }


    public Map<String, Object> getDataMap() {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("doc_gdbh", "哎呦喂哈哈");
        dataMap.put("sj_Djdh", "哎呦喂哈哈");
        //dataMap.put("doc_ldsj", "我擦勒");
//        try {
//            dataMap.put("image", getImageStr("D:\\头像.jpg"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return dataMap;
    }

    public void exportDoc(String doc, String name) throws Exception {
        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(doc), encoding));
        Template template = getTemplate(name);
        template.process(getDataMap(), writer);
    }

    /**
     * 测试方法
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ExportDoc maker = new ExportDoc("UTF-8");
        maker.exportDoc("C:/Users/DELL/Desktop/11.docx", "wordExport.ftl");
        //maker.
    }


    /**
     * 浏览器导出word
     *
     * @param modelName 模板文件名（包含后缀ftl）
     * @param fileName  导出文件名
     * @param dataMap   数据Map
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     */
    public void downloadDoc(String modelName, String fileName, Map dataMap, HttpServletRequest request, HttpServletResponse response) {
        Writer writer = null;
        String filename = "";

        try {
            //编码文件名并加上后缀
            filename = encodeChineseDownloadFileName(request, fileName) + ".doc";
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //设置HTTP响应头
            //response.setContentType("application/msword");
            //response.addHeader("Content-Disposition","attachment; filename="+"haha");
            response.setHeader("Content-disposition", filename);
            response.setContentType("application/msword");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            response.setHeader("Pragma", "No-cache");
            writer = response.getWriter();
            //writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\ttt.doc"), encoding));
            Template template = this.getTemplate(modelName);
            //template.process(this.getSjDataMap(), writer);
            template.process(dataMap, writer);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
     *
     * @param request
     * @param pFileName
     * @return
     * @throws UnsupportedEncodingException
     */

    public static String encodeChineseDownloadFileName(
            HttpServletRequest request, String pFileName) throws Exception {

        String filename = null;
        String agent = request.getHeader("USER-AGENT");
        if (null != agent) {
            if (-1 != agent.indexOf("Firefox")) {//Firefox
                filename = "=?UTF-8?B?" + (new String(org.apache.commons.codec.binary.Base64.encodeBase64(pFileName.getBytes("UTF-8")))) + "?=";
            } else if (-1 != agent.indexOf("Chrome")) {//Chrome
                filename = new String(pFileName.getBytes(), "ISO8859-1");
            } else {//IE7+
                filename = java.net.URLEncoder.encode(pFileName, "UTF-8");
                filename = filename.replace("+", "%20");
            }
        } else {
            filename = pFileName;
        }
        return filename;
    }

    /**
     * 通过模板获取word流
     *
     * @param modelName 模板文件名（包含后缀ftl）
     * @param dataMap   数据Map
     */
    public ByteArrayOutputStream wordBaos(String modelName, Map dataMap) {
        ByteArrayOutputStream byteOut = null;
        Writer writer = null;
        try {
            byteOut = new ByteArrayOutputStream();
            Template template = this.getTemplate(modelName);
            writer = new OutputStreamWriter(byteOut, "UTF-8");
            template.process(dataMap, writer);
            return byteOut;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将多个文件流合并到压缩包下载
     * @param baoss 字节输出流数组
     * @param fileNames 文件名数组
     * @param response HttpServletResponse
     */
    /*public void downLoadZip(ByteArrayOutputStream[] baoss, String[] fileNames, String zipName, HttpServletRequest request, HttpServletResponse response){
        //压缩包输出流
        ZipArchiveOutputStream zous = null;
        try {
            zous = new ZipArchiveOutputStream(response.getOutputStream());
            zous.setUseZip64(Zip64Mode.AsNeeded);
            //response.setContentType("application/octet-stream");
            //编码文件名并加上后缀(压缩包内部文件名不需要编码)
            String filename = encodeChineseDownloadFileName(request, zipName) + ".zip";
            response.setHeader("Content-disposition", "attachment; filename=" + filename);
            for(int i = 0; i < baoss.length; i++){
                ByteArrayOutputStream baos = baoss[i];
                //给文件名
                String fileName = fileNames[i];
                //下面三行是吧excel的文件以流的形式转为byte[]
                byte[] bytes = baos.toByteArray();
                ArchiveEntry entry = new ZipArchiveEntry(fileName);
                zous.putArchiveEntry(entry);
                zous.write(bytes);
                zous.closeArchiveEntry();
                if (baos != null) {
                    baos.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(zous != null){
                    zous.flush();
                    zous.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/
}
