package com.backstage.util;


import java.util.Properties;
import java.util.Random;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;


public class MailSend {

    //邮件发送
    //参数一:接受者邮件地址 参数二:发送信息
    public static void send(String yourMail, String massage) {
        //准备发送的账户和密码(授权码)
        String myMail = "478577385@qq.com";//这里我使用的是qq邮箱
        String password = "dyutgddprkynbjjf";//授权码登录邮箱之后可以自己验证后生成一个激活码（授权码）
        //创建和smtp服务器的链接
        String host = "smtp.qq.com";//对应的2邮箱有对应的smtp服务地址
        //通过给定的链接服务器地址创建链接
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", 25);
        Session session = Session.getInstance(props);
        //开启打印信息
        session.setDebug(true);
        try {
            //构建链接
            Transport transport = session.getTransport("smtp");
            transport.connect(myMail, password);

            //创建邮件内容
            MimeMessage mimeMessage = new MimeMessage(session);

            //设置发送地址
            InternetAddress fromAddress = new InternetAddress(myMail);
            mimeMessage.setFrom(fromAddress);

            //设置接收人
            InternetAddress toAddress = new InternetAddress(yourMail);
            mimeMessage.setRecipient(RecipientType.TO, toAddress);

            //设置邮件主题
            mimeMessage.setSubject("邮箱激活");

            //设置发送内容
            mimeMessage.setContent(massage, "text/html;charset=UTF-8");

            //发送邮件
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();

            System.out.println("send mail success");
        } catch (Exception e) {
            System.out.println("send mail fail !!");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //生成随机的数（作为验证码发送）
    public static String getRandom(int n) {
        char[] code = "0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(code[new Random().nextInt(code.length)]);
        }
        return sb.toString();
    }

    //发送邮箱激活码
    public static String getMail(String random, String yourMail) {

        String mailContent = "邮箱验证，您的验证码为：" + random + "，请您尽快完成验证，验证码请勿告诉别人！";
        //调用发送邮箱的方法
        send(yourMail, mailContent);
        return random;
    }

    public static void main(String[] args) {

        String random = getRandom(6);
        //存储邮箱随机数
        getMail(random,"15961761901@163.com");



        //接收的邮箱什么邮箱都可以
        send("15961761901@163.com", "激活码：888888");
        send("15961761901@163.com","<a href='http://172.16.8.6:8989/dangdang_zhangcn/book/main'>点击验证邮箱</a>");
    }
}
