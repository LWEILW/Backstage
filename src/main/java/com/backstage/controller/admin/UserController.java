package com.backstage.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.backstage.service.ShareService;
import com.backstage.util.ExcelUtil;
import com.backstage.util.FastDFSClientUtil;
import com.backstage.util.word.WordExport;
import com.backstage.util.word.WordExportHtml;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.admin.User;
import com.backstage.service.admin.UserService;
import com.backstage.util.Result;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 用户管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 */
@Api(value = "[APP-测试接口]-FrontExaminationReportController")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private FastDFSClientUtil dfsClient;

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 用户台账
     *
     * @param data
     * @return
     */
    @PostMapping("/userList")
    @RequiresPermissions("userList")
    public Result userList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");
        User user = JSONObject.parseObject(data, User.class);

        Page<User> page = new Page<>(currentPage, pageSize);
        page = userService.userList(page, user);
        return Result.success("用户台账")
                .data("userList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping("/detailsUser/{userId}")
    @RequiresPermissions("detailsUser")
    public Result detailsUser(@PathVariable("userId") int userId) {
        // 用户详情
        User user = userService.detailsUser(userId);
        // 用户所选角色列表
        JSONArray roleList = userService.getRolesByUserId(userId);

        return Result.success("用户详情")
                .data("user", user)
                .data("roleList", roleList);
    }


    /**
     * 用户保存
     *
     * @param data
     * @return
     */
    @PostMapping("/createOrUpdateUser")
    @RequiresPermissions("createOrUpdateUser")
    public Result createOrUpdateUser(@RequestBody String data) {
        try {
            JSONObject obj = JSONObject.parseObject(data);
            User user = JSON.parseObject(data, User.class);
            JSONArray roleList = (JSONArray) obj.get("roleList");

            Boolean result = userService.createOrUpdateUser(user, roleList);
            if (result) {
                return Result.success("保存成功");
            } else {
                return Result.fail("保存失败");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Result.fail("保存失败");
        }
    }


    /**
     * 用户删除
     *
     * @param userId
     * @return
     */
    @GetMapping("/deleteUser/{userId}")
    @RequiresPermissions("deleteUser")
    public Result deleteUserById(@PathVariable int userId) {

        Boolean result = userService.deleteUserById(userId);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }


    /**
     * 用户批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteUser/list")
    @RequiresPermissions("deleteUser")
    public Result deleteUserList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        List<User> userList = JSON.parseArray(obj.getString("userList"), User.class);


        Boolean result = userService.deleteUserList(userList);
        if (result) {
            return Result.success("删除成功");
        } else {
            return Result.fail("删除失败");
        }
    }


    /**
     * 用户改变状态
     *
     * @param data
     * @return
     */
    @PostMapping("/changeUserStatus")
    public Result changeUserStatus(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        Integer userId = obj.getInteger("userId");
        Integer userStatus = obj.getInteger("userStatus");

        int count = shareService.updateOne("user", "userStatus", userStatus.toString(), "userId=" + userId);
        if (count == 1) {
            return Result.success("改变成功");
        } else {
            return Result.fail("改变成功");
        }
    }


    /**
     * 用户重置密码
     *
     * @param userId
     * @return
     */
    @GetMapping("/resetPassword/{userId}")
    public Result resetPassword(@PathVariable int userId) {

        Boolean result = userService.resetPassword(userId);
        if (result) {
            return Result.success("重置成功");
        } else {
            return Result.fail("重置失败");
        }
    }


    /**
     * 用户模板下载
     *
     * @param response response响应
     * @return Result
     * @throws IOException
     * @author Zhu Jiabing
     */
    @RequestMapping("downloadUserModel")
    public Result downloadUserModel(HttpServletResponse response) throws IOException {
        // 模板路径26:/eams/fastdfs/file/date/ff/ff

        String fileUrl = "group1/M00/FF/FF/用户上载模板.xlsx";
        String group = fileUrl.substring(0, fileUrl.indexOf("/"));
        String path = fileUrl.substring(fileUrl.indexOf("/") + 1);
        DownloadByteArray downloadByteArray = new DownloadByteArray();
        byte[] bytes = storageClient.downloadFile(group, path, downloadByteArray);

        // 这里只是为了整合fastdfs，所以写死了文件格式。需要在上传的时候保存文件名。下载的时候使用对应的格式
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("用户上载模板.xlsx", "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            outputStream.write(bytes);
            return Result.success("downloadTransferTemp");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("downloadTransferTemp");
        } finally {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * 用户模板下载
//     *
//     * @param response response响应
//     * @return Result
//     * @throws IOException
//     * @author Zhu Jiabing
//     */
//    @RequestMapping("downloadUserModel")
//    public Result downloadUserModel(HttpServletResponse response) throws IOException {
//        // 模板路径26:/eams/fastdfs/file/date/ff/ff
//
//        String fileUrl = "group1/M00/FF/FF/电能消耗上载模板.xlsx";
//        byte[] bytes = dfsClient.downloadFile(fileUrl);
//        // 这里只是为了整合fastdfs，所以写死了文件格式。需要在上传的时候保存文件名。下载的时候使用对应的格式
//        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode("电能消耗上载模板.xlsx", "UTF-8"));
//        response.setCharacterEncoding("UTF-8");
//        ServletOutputStream outputStream = null;
//        try {
//            outputStream = response.getOutputStream();
//            outputStream.write(bytes);
//            return Result.success("downloadTransferTemp");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return Result.fail("downloadTransferTemp");
//        } finally {
//            try {
//                outputStream.flush();
//                outputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    /**
     * 用户Excel导入
     *
     * @param file     Excel文件
     * @param response 响应
     * @return Result
     * @throws IOException
     */
    @RequestMapping("importUserExcel")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result importUserExcel(MultipartFile file, HttpServletResponse response) throws IOException {
        // 反馈excel文件名
        String fileName = "用户信息上载回馈单" + System.currentTimeMillis() + ".xlsx";

        Workbook workbook = userService.importUserExcel(file);

        // 响应到客户端
        try {
            OutputStream output = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            // 设置contentType为excel格式
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "Attachment;Filename=" + new String(fileName.getBytes(), StandardCharsets.UTF_8));

            workbook.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 用户Excel导出
     *
     * @param file     Excel文件
     * @param response 响应
     * @return Result
     * @throws IOException
     */
    @RequestMapping("importPowerConsumptionExcel")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Result importPowerConsumptionExcel(MultipartFile file, HttpServletResponse response) throws IOException {
        // 反馈excel文件名
        String fileName = "电能消耗上载回馈单" + System.currentTimeMillis() + ".xlsx";

        Workbook workbook = userService.importUserExcel(file);

        // 响应到客户端
        try {
            OutputStream output = response.getOutputStream();
            response.setCharacterEncoding("UTF-8");
            // 设置contentType为excel格式
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "Attachment;Filename=" + new String(fileName.getBytes(), StandardCharsets.UTF_8));

            workbook.write(output);
            output.flush();
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 用户Word文档-html格式
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @GetMapping("wordExportHtml")
    public void wordExportHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("publish", "用户名称");
        params.put("status", "用户状态");

        // html导出word文档
        WordExportHtml.exportWord(request, response, "用户信息word文档", params);

    }


    /**
     * 用户Word文档
     *
     * @param request          HttpServletRequest
     * @param response         HttpServletResponse
     * @param operationDailyId int
     */
    @GetMapping("wordExport")
    public void wordExport(HttpServletRequest request, HttpServletResponse response, @PathVariable("operationDailyId") int operationDailyId) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("publish", "用户名称");
        params.put("status", "用户状态");

        // html导出word文档
        WordExport.exportWord("/root/operateWord.docx", "/root", "用户信息word文档.docx", params, request, response);

    }


    @RequestMapping("/downUser")
    public void downUser(HttpServletRequest request, HttpServletResponse response, @RequestBody String data) throws IOException, ParseException {
        // 设置响应和请求编码utf-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<User> userList = JSONArray.parseArray("data", User.class);

        //复制Excel，填写信息
        String fileName = new ExcelUtil().export(userList);


        File file = new File(fileName);
        if (file.exists()) {
            response.setContentType("application/force-download");
            // 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=complete_" + "用户信息测试.xlsx");// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(HttpServletRequest request, HttpServletResponse response) {

        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setUserId(1);
        user1.setUserName("nihao");
        userList.add(user1);
        User user2 = new User();
        user2.setUserId(2);
        user2.setUserName("nihao2");
        userList.add(user2);

        Workbook wb = null;
        try {
            // excel模板路径

            String excel = "/excel/user_model.xlsx";
            File fi = new File(excel);
            // 读取excel模板
            wb = new XSSFWorkbook(new FileInputStream(fi));
            // 读取了模板内所有sheet内容
            Sheet sheet = wb.getSheetAt(0);
            // 在相应的单元格进行赋值

            for (int i = 0; userList.size() > i; i++) {
                // 用户ID
                int userId = userList.get(i).getUserId();
                // 用户名称
                String userName = userList.get(i).getUserName();

                sheet.getRow(i).getCell(10).setCellValue(userId);
                sheet.getRow(2).getCell(7).setCellValue(userId);
                sheet.getRow(3).getCell(7).setCellValue(1);
                sheet.getRow(4).getCell(7).setCellValue(2);
                sheet.getRow(5).getCell(7).setCellValue(3);
                sheet.getRow(6).getCell(6).setCellValue("2324234");
            }

            String fileName = "用户信息";
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);


            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream sout = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(sout);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (Exception e) {

            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }

        } catch (Exception e) {

        }
    }


    @RequestMapping(value = "/testExport", method = RequestMethod.GET)
    public void export2007(HttpServletRequest request, HttpServletResponse response) {
        List<User> list = new ArrayList<User>();
        User user1 = new User();
        user1.setUserId(1111);
        list.add(user1);

        User user2 = new User();
        user2.setUserId(12222);
        list.add(user2);

        User user3 = new User();
        user3.setUserId(13333);
        list.add(user3);

        User user4 = new User();
        user4.setUserId(14444);
        list.add(user4);

        XSSFWorkbook wb = null;
        try {
            // excel模板路径
            String fileUrl = "group1/M00/FF/FF/用户上载模板.xlsx";
            String group = fileUrl.substring(0, fileUrl.indexOf("/"));
            String path = fileUrl.substring(fileUrl.indexOf("/") + 1);

            String excel = "C:/Users/DELL/Desktop/用户上载模板.xlsx";
            File fi = new File(excel);
            // 读取excel模板
            wb = new XSSFWorkbook(new FileInputStream(fi));
            // 读取了模板内所有sheet内容
            Sheet sheet = wb.getSheetAt(0);
            // 在相应的单元格进行赋值
            int rowIndex = 1;
            int j = 1;
            for (User user : list) {
                Row row = sheet.getRow(rowIndex);
                if (null == row) {
                    row = sheet.createRow(rowIndex);
                }
                Cell cell0 = row.getCell(0);
                if (null == cell0) {
                    cell0 = row.createCell(0);
                }
                cell0.setCellValue(user.getUserId());// 标识


                Cell cell2 = row.getCell(3);
                if (null == cell2) {
                    cell2 = row.createCell(3);
                }
                cell2.setCellValue(user.getUserId());// 标识


                Cell cell3 = row.getCell(4);
                if (null == cell3) {
                    cell3 = row.createCell(4);
                }
                cell3.setCellValue(user.getUserId());// 标识


                rowIndex++;
            }

            String fileName = "用户信息";
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream sout = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;

            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(sout);
                byte[] buff = new byte[2048];
                int bytesRead;
                // Simple read/write loop.
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    bis.close();
                }
                if (bos != null) {
                    bos.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
