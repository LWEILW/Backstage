package com.backstage.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author geQiang
 * @version 1.0
 * @date 2019/6/12
 * @description cn.it58.util
 */
public class WordUtils {

    public void getUserWord(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        //1、创建配置文件对象
        Configuration configuration = new Configuration();
        //2、设置字符集
        configuration.setDefaultEncoding("UTF-8");
        //3、指定模板所在的文件夹
        configuration.setServletContextForTemplateLoading(request.getSession().getServletContext(), "/template");
        //4、获得模板对象
        Template t = null;
        try {
            t = configuration.getTemplate("resume.xml", "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //5、创建临时word文件
        String fileName = (String) map.get("userName") + ".doc";
        File file = new File(fileName);
        //6、替换模板中的标记${id},${userName}...
        Writer writer = null;
        try {
            //Writer：字符流接口，父类
            //BufferedWriter:字符缓冲流，构造时需要Writer类型对象入参
            //OutputStreamWriter:以字节读取文件转化成字符文件时，同时进行设置字符集，
            //构造时需要OutputStream对象入参
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            t.process(map, writer);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //7、读取word文档到缓冲区
        InputStream inputStream = null;
        OutputStream outputStream = null;
        byte[] buffer = new byte[0];
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            int len = inputStream.available();
            buffer = new byte[len];
            inputStream.read(buffer);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //8、重置response对象
        response.reset();
        //9、设置响应内容的类型
        response.setContentType("application/octet-stream");
        //10、设置响应头提示下载指定文件
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //11、设置响应头提示文件长度
        response.setHeader("Content-Length", "" + file.length());
        //12、打开输出流，转接到响应流
        try {
            outputStream = new BufferedOutputStream(response.getOutputStream());
            //13、输出缓冲区的word文档
            outputStream.write(buffer);
            outputStream.flush();
            //14、关闭流
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
