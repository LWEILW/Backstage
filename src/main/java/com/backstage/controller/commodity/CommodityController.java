package com.backstage.controller.commodity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.backstage.controller.AdminController;
import com.backstage.entity.commodity.Commodity;
import com.backstage.service.commodity.CommodityService;
import com.backstage.util.MD5Util;
import com.backstage.util.Result;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.Wrapper;
import java.util.List;

/**
 * 商品管理
 *
 * @author Liu wei
 * @date 2020-07-22 16:00
 **/
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityService commodityService;


//    @Value("${URL.tomcat.ip}")
//    private String ip;
//
//
//    @Value("${URL.tomcat.port}")
//    private String port;

    /**
     * 商品台账
     *
     * @param data
     * @return
     */
    @PostMapping("/commodityList")
    public Result commodityList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        Page<Commodity> page = new Page<>(currentPage, pageSize);
        page = commodityService.commodityList(page);
        return Result.success("商品台账")
                .data("commodityList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 商品详情
     *
     * @param commodityId
     * @return
     */
    @GetMapping("/commodityDetails/{commodityId}")
    public Result commodityDetails(@PathVariable("commodityId") int commodityId) {

        Commodity commodity = commodityService.commodityDetails(commodityId);
        return Result.success("商品详情")
                .data("commodity", commodity);
    }


    /**
     * 商品新建/编辑
     *
     * @param data
     * @return
     */
    @PostMapping("/commoditySave")
    public Result commoditySave(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Commodity commodity = JSONObject.parseObject(data, Commodity.class);
        List<JSONObject> productImages = JSON.parseArray(obj.getString("productImages"), JSONObject.class);


        Boolean result = commodityService.commoditySave(commodity, productImages);
        if (result) {
            return Result.success("保存成功");
        } else {
            return Result.fail("保存失败");
        }
    }


    /**
     * 商品批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteCommodityAll")
    public Result deleteCommodityAll(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        JSONArray idList = (JSONArray) obj.get("idList");

        Boolean result = commodityService.commodityDeleteAll(idList);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }

    /**
     * 商品图片上传判断
     *
     * @param req
     * @param multiReq
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/pathFileExists")
    public Result pathFileExists(HttpServletRequest req, MultipartHttpServletRequest multiReq)
            throws IOException {
        String fileUploadPath = "C:\\Users\\DELL\\Desktop\\";

        MultipartFile file = multiReq.getFile("file");
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
        String localFileName = MD5Util.md5(file.getInputStream()) + suffix;
        File localFile = new File(fileUploadPath + localFileName);
        if (localFile.exists()) {
            return Result.fail("文件已存在！！");
        }
        return Result.success("文件已存在！！");
    }

    /**
     * 商品图片上传
     *
     * @param req
     * @param multiReq
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/imgUpload")
    public Result imgUpload(HttpServletRequest req, MultipartHttpServletRequest multiReq)
            throws IOException {
        String fileUploadPath = "C:\\Users\\DELL\\Desktop\\";
        //我这里用的springboot 在application.properties中配置，使用@Value 获取的文件上传目录
        System.out.println("---" + fileUploadPath);

        MultipartFile file = multiReq.getFile("file");
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.indexOf("."));
            String localFileName = MD5Util.md5(file.getInputStream()) + suffix;
        File localFile = new File(fileUploadPath + localFileName);
        if (localFile.exists()) {
            return Result.fail("文件已存在！！")
                    .data("path", "http://localhost:8080/img/" + localFileName);
        }

        localFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(localFile);
        InputStream fs = file.getInputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = fs.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        fs.close();
        return Result.success("上传成功")
                .data("path", "http://localhost:8080/img/" + localFileName);

    }

}
