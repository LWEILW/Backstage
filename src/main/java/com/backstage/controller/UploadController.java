package com.backstage.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.backstage.entity.admin.User;
import com.backstage.util.ExcelUtil;
import com.backstage.util.FastDFSClientUtil;
import com.backstage.util.Result;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.github.tobato.fastdfs.service.FastFileStorageClient;


@Controller
public class UploadController {

    @Autowired
    private FastDFSClientUtil dfsClient;

    @Autowired
    private FastFileStorageClient storageClient;


    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String fdfsUpload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

        try {
            String fileUrl = dfsClient.uploadFile(file);
            request.setAttribute("msg", "成功上传文件，  '" + fileUrl + "'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }


    /*
     * http://localhost/download?filePath=group1/M00/00/00/wKgIZVzZEF2ATP08ABC9j8AnNSs744.jpg
     */
    @RequestMapping("/download")
    public void download(String filePath, HttpServletRequest request, HttpServletResponse response) throws IOException {

        //    group1/M00/00/00/wKgIZVzZEF2ATP08ABC9j8AnNSs744.jpg
        String[] paths = filePath.split("/");
        String groupName = null;
        for (String item : paths) {
            if (item.indexOf("group") != -1) {
                groupName = item;
                break;
            }
        }
        String path = filePath.substring(filePath.indexOf(groupName) + groupName.length() + 1, filePath.length());
        InputStream input = dfsClient.download(groupName, path);

        //根据文件名获取 MIME 类型
        String fileName = paths[paths.length - 1];
        System.out.println("fileName :" + fileName); // wKgIZVzZEF2ATP08ABC9j8AnNSs744.jpg
        String contentType = request.getServletContext().getMimeType(fileName);
        String contentDisposition = "attachment;filename=" + fileName;

        // 设置头
        response.setHeader("Content-Type", contentType);
        response.setHeader("Content-Disposition", contentDisposition);

        // 获取绑定了客户端的流
        ServletOutputStream output = response.getOutputStream();

        // 把输入流中的数据写入到输出流中
        IOUtils.copy(input, output);
        input.close();

    }

    /**
     * 11.模板 - 下载
     *
     * @return com..util.Result
     * @author
     */
    @GetMapping("/itemTemplateDownload")
    public Result itemTemplateDownload(HttpServletResponse response) throws IOException {
        String fileUrl = "group1/M00/FF/FF/物资主数据上载模板.xlsx";
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = storageClient.downloadFile(group, path, downloadByteArray);

        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("物资主数据上载模板.xlsx", "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("模板下载失败");
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * http://localhost/deleteFile?filePath=group1/M00/00/00/wKgIZVzZaRiAZemtAARpYjHP9j4930.jpg
     *
     * @param fileName group1/M00/00/00/wKgIZVzZaRiAZemtAARpYjHP9j4930.jpg
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/deleteFile")
    public String delFile(String filePath, HttpServletRequest request, HttpServletResponse response) {

        try {
            dfsClient.delFile(filePath);
        } catch (Exception e) {
            // 文件不存在报异常 ： com.github.tobato.fastdfs.exception.FdfsServerException: 错误码：2，错误信息：找不到节点或文件
            // e.printStackTrace();
        }
        request.setAttribute("msg", "成功删除，'" + filePath);

        return "index";
    }




}
