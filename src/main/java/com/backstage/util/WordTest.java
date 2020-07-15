package com.backstage.util;//package com.blogger.util;
//
//import com.blogger.util.word.RichHtmlHandler;
//import com.blogger.util.word.RichObject;
//import com.blogger.util.word.WordGeneratorWithFreemarker;
//import freemarker.template.Template;
//
//import java.io.BufferedWriter;
//import java.io.Writer;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class WordTest {
//
//
//    public void aaa() {
//        //用map存放数据
//        HashMap<String, Object> data = new HashMap<String, Object>();
//        //创建富文本
//        StringBuilder sb = new StringBuilder();
//        sb.append("<div>");
//        sb.append("<img style='height:100px;width:200px;display:block;' src='w:\\2.jpg' />");
//        sb.append("</br><span>wesley 演示 导出富文本！@@#######￥￥%%%%………………&&&**~~~~~~&&&&&&&&、、、、、、、、</span>");
//        sb.append("</br><span>----多图分割线---</span>");
//        sb.append("</br><img style='height:100px;width:200px;display:block;' src='w:\\1.jpg' />");
//        sb.append("</br><span>中国梦，幸福梦！</span>");
//        sb.append("</div>");
//
//        RichObject richObject = new RichObject();
//        richObject.setHtml(sb.toString());
//        //--------------------此处可以spring配置文件配置，也可以直接读取属性文件获取------------------
//        //从mht文件中找
//        richObject.setDocSrcLocationPrex("file:///C:/268D4AA4"); //这里是从mht中获取的资源文件所在的文件夹
//        richObject.setDocSrcParent("word.files"); //资源文件夹名字
//        richObject.setNextPartId("01D2C8DD.BC13AF60"); //下一部分的ID
//        //以下三个属性字段我也不是很懂 查询网上是这么用的 不过根据字段应该大致能猜到是做什么用的。
//        richObject.setShapeidPrex("_x56fe__x7247__x0020");
//        richObject.setTypeid("#_x0000_t75");
//        richObject.setSpidPrex("_x0000_i");
//
//        richObject.setWebAppliction(false);
//
//        //这里封装了一个Hnadler处理对象，来处理数据。
//        RichHtmlHandler richHtmlHandler = WordGeneratorWithFreemarker.createRichHtmlHandler(richObject);
//        List<RichHtmlHandler> richHtmlHandlerList = new ArrayList<RichHtmlHandler>();
//        richHtmlHandlerList.add(richHtmlHandler);
//        //这里就是我们刚才加的两个字段，也是我们富文本文件处理的关键两个字段
//        data.put("imagesXmlHrefString", WordGeneratorWithFreemarker.getXmlImgHref(richHtmlHandlerList));//
//        logger.debug("------imagesXmlHrefString-------" + WordGeneratorWithFreemarker.getXmlImgHref(richHtmlHandlerList));
//        data.put("imagesBase64String", WordGeneratorWithFreemarker.getImagesBase64String(richHtmlHandlerList));
//        logger.debug("------imagesBase64String-------" + WordGeneratorWithFreemarker.getImagesBase64String(richHtmlHandlerList));
//        data.put("name", "wesley");
//        data.put("datetime", "2017-05-10");
//        data.put("title", "演示demo");
//        data.put("context1", richHtmlHandler.getHandledDocBodyBlock());
//        data.put("context2", richHtmlHandler.getHandledDocBodyBlock());
//        data.put("context3", richHtmlHandler.getHandledDocBodyBlock());
//        data.put("context4", richHtmlHandler.getHandledDocBodyBlock());
//        data.put("context5", richHtmlHandler.getHandledDocBodyBlock());
//        data.put("context6", richHtmlHandler.getHandledDocBodyBlock());
//
//        String docFilePath = "w:\\temp_by_wesley.doc";//目标文件
//        String templatePath = Class.class.getResource("/ftl").getPath();
//        templatePath = java.net.URLDecoder.decode(templatePath, "utf-8");//这里我的路径有空格添加此处理
//        logger.debug("------templatePath-------" + templatePath);
//        WordGeneratorWithFreemarker.createDoc(templatePath, "word.ftl", data, docFilePath);
//    }
//
//    /**
//     * 创建doc文件
//     * [@param](https://my.oschina.net/u/2303379) templatePath 模板所在路径 xxx/xxx/template
//     * [@param](https://my.oschina.net/u/2303379) templateName 模板名字 xxx.ftl
//     * [@param](https://my.oschina.net/u/2303379) dataMap 数据集合
//     * [@param](https://my.oschina.net/u/2303379) outPath 输出文件路径  xxx/xxx/xxx.doc
//     */
//    public static void createDoc(String templatePath, String templateName, Map<String, Object> dataMap, String outPath) throws Exception {
//        logger.debug("WordGeneratorWithFreemarker createDoc()");
//        Freemarker.fprint(templatePath, templateName, dataMap, outPath);
//    }
//
//    /**
//     * 基于文件的输出
//     *
//     * @param templatePath 模板所在路径 xxx/xxx/template
//     * @param templateName 模板名字 xxx.ftl
//     * @param dataMap      数据集合
//     * @param outPath      输出文件路径  xxx/xxx/xxx.doc
//     */
//    public static void fprint(String templatePath, String templateName, Map<String, Object> root, String outPath) {
//        logger.debug("Freemarker fprint file");
//        try {
//            getInstance(templatePath);
//            Template template = getTemplate(templateName);
//            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(outPath)), "UTF-8"));
//            template.process(root, out);
//            out.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        } catch (TemplateException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
//
//}
