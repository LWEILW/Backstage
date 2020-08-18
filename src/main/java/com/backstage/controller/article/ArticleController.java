package com.backstage.controller.article;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.backstage.entity.article.Article;
import com.backstage.service.article.ArticleService;
import com.backstage.util.*;
import com.backstage.util.word.RichHtmlHandler;
import com.backstage.util.word.RichObject;
import com.backstage.util.word.WordGeneratorWithFreemarker;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 文章管理
 *
 * @author Liu wei
 * @date 2020-03-31 16:00
 **/
@RestController
@RequestMapping("/article")
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);


    @Autowired
    private ArticleService articleService;

    /**
     * 文章台账
     *
     * @param data
     * @return
     */
    @PostMapping("/articleList")
    @RequiresPermissions("articleList")
    public Result articleList(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        int currentPage = obj.getInteger("currentPage");
        int pageSize = obj.getInteger("pageSize");

        Page<Article> page = new Page<>(currentPage, pageSize);
        page = articleService.articleList(page);
        return Result.success("博客文章台账")
                .data("articleList", page.getRecords())
                .data("total", page.getTotal())
                .data("pages", page.getPages())
                .data("currentPage", currentPage);
    }


    /**
     * 文章详情
     *
     * @param articleId
     * @return
     */
    @GetMapping("/articleDetails/{articleId}")
    @RequiresPermissions("articleDetails")
    public Result articleDetails(@PathVariable("articleId") int articleId) {
        Article article = articleService.articleDetails(articleId);
        return Result.success("文章详情")
                .data("article", article);
    }


    /**
     * 文章新建/编辑
     *
     * @param data
     * @return
     */
    @PostMapping("/articleSave")
    @RequiresPermissions("articleSave")
    public String articleSave(@RequestBody String data) {
        Article article = JSONObject.parseObject(data, Article.class);

        boolean succ = articleService.articleSave(article);
        if (succ) {
            return "保存成功";
        } else {
            return "保存失败";
        }
    }


    /**
     * 文章删除
     *
     * @param articleId
     * @return
     */
    @GetMapping("/articleDelete/{articleId}")
    @RequiresPermissions("articleDelete")
    public String articleDelete(@PathVariable("articleId") int articleId) {

        int succ = articleService.articleDelete(articleId);
        if (succ == 1) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }


    /**
     * 文章批量删除
     *
     * @param data
     * @return
     */
    @PostMapping("/deleteArticleAll")
    @RequiresPermissions("deleteArticleAll")
    public String deleteArticleAll(@RequestBody String data) {
        JSONObject obj = JSONObject.parseObject(data);
        JSONArray idList = (JSONArray) obj.get("idList");

        boolean succ = articleService.articleDeleteAll(idList);
        if (succ) {
            return "删除成功";
        } else {
            return "删除失败";
        }
    }


    /**
     * 运营日报导出Word文档
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @GetMapping("/wordExport")
    public void wordExport(HttpServletRequest request, HttpServletResponse response) throws ParseException, Exception {
//        // 第一种
//        Map<String, Object> params = new HashMap<>();
//        // 线路
//        params.put("dailySiteName", "1");
//        // 日期
//        params.put("dailyDate", "1");
//        // 表名
//        params.put("dailyName", "1");
//        // 更新时间
//        params.put("updateDate", "1");
//
//        System.out.println("1");
//
//        // 工具类生成word文档（本地）
//        WordExport.exportWord("C:/Users/DELL/Desktop/dome.docx", "C:/Users/DELL/Desktop", "123456.docx",
//                params, request, response);
//

        // 第二种
        String content = "<div>\n" +
                "    <div style=\"border-top: 1px solid; border-left: 1px solid; \" class=\"title-name\">\n" +
                "        2020年06月16日佛山运营日报\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"sub_title\">\n" +
                "        <div class=\"line line-input line-clear-border\"> " +
                "           更新时间：2020-06-16 13:20:39" +
                "       </div>\n" +
                "    </div>\n" +
                "\n" +
                "</div>";


        WordUtil.exportWord(request, response, "运营日报", content);

    }


    public static void a() throws Exception {
        //用map存放数据
        HashMap<String, Object> data = new HashMap<String, Object>();
        //创建富文本
        StringBuilder sb = new StringBuilder();
        sb.append("<div>");
//        sb.append("<img style='height:100px;width:200px;display:block;' src='w:\\2.jpg' />");
        sb.append("</br><span>wesley 演示 导出富文本！@@#######￥￥%%%%………………&&&**~~~~~~&&&&&&&&、、、、、、、、</span>");
        sb.append("</br><span>----多图分割线---</span>");
//        sb.append("</br><img style='height:100px;width:200px;display:block;' src='w:\\1.jpg' />");
        sb.append("</br><span>中国梦，幸福梦！</span>");
        sb.append("</div>");

        RichObject richObject = new RichObject();
        richObject.setHtml(sb.toString());
        //--------------------此处可以spring配置文件配置，也可以直接读取属性文件获取------------------
        //从mht文件中找
        richObject.setDocSrcLocationPrex("file:///C:/268BA2D4"); //这里是从mht中获取的资源文件所在的文件夹
        richObject.setDocSrcParent("test.files"); //资源文件夹名字
        richObject.setNextPartId("01D642FE.58A3E230"); //下一部分的ID
        //以下三个属性字段我也不是很懂 查询网上是这么用的 不过根据字段应该大致能猜到是做什么用的。
        richObject.setShapeidPrex("_x56fe__x7247__x0020");
        richObject.setTypeid("#_x0000_t75");
        richObject.setSpidPrex("_x0000_i");

        richObject.setWebAppliction(false);

        //这里封装了一个Hnadler处理对象，来处理数据。
        RichHtmlHandler richHtmlHandler = WordGeneratorWithFreemarker.createRichHtmlHandler(richObject);
        List<RichHtmlHandler> richHtmlHandlerList = new ArrayList<RichHtmlHandler>();
        richHtmlHandlerList.add(richHtmlHandler);
        //这里就是我们刚才加的两个字段，也是我们富文本文件处理的关键两个字段
        data.put("imagesXmlHrefString", WordGeneratorWithFreemarker.getXmlImgHref(richHtmlHandlerList));//
        logger.debug("------imagesXmlHrefString-------" + WordGeneratorWithFreemarker.getXmlImgHref(richHtmlHandlerList));
        data.put("imagesBase64String", WordGeneratorWithFreemarker.getImagesBase64String(richHtmlHandlerList));
        logger.debug("------imagesBase64String-------" + WordGeneratorWithFreemarker.getImagesBase64String(richHtmlHandlerList));
        data.put("name", "wesley");
        data.put("datetime", "2017-05-10");
        data.put("title", "演示demo");
        data.put("context1", richHtmlHandler.getHandledDocBodyBlock());
        data.put("context2", richHtmlHandler.getHandledDocBodyBlock());
        data.put("context3", richHtmlHandler.getHandledDocBodyBlock());
        data.put("context4", richHtmlHandler.getHandledDocBodyBlock());
        data.put("context5", richHtmlHandler.getHandledDocBodyBlock());
        data.put("context6", richHtmlHandler.getHandledDocBodyBlock());

        data.put("aaa", richHtmlHandler.getHandledDocBodyBlock());
        data.put("bbb", "");
        data.put("ccc", "");
        data.put("ddd", "");
        data.put("eee", "");
        data.put("fff", "");


        String docFilePath = "C:/Users/DELL/Desktop/test.docx";//目标文件
        String templatePath = "C:/Users/DELL/Desktop";
        templatePath = java.net.URLDecoder.decode(templatePath, "utf-8");//这里我的路径有空格添加此处理
        logger.debug("------templatePath-------" + templatePath);
        WordGeneratorWithFreemarker.createDoc(templatePath, "test.ftl", data, docFilePath);
    }


}
