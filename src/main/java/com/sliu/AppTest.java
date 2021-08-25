package com.sliu;

import com.sliu.util.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest {

    @Autowired
    MailService mailService;

    @Test
    public void sendSimpleMail(){
        System.out.print("测试发送邮件");
        mailService.sendSimpleMail("liushuai152987@qq.com",
                "804982197@qq.com","1470249098@qq.com","测试邮件主题","测试邮件内容");
    }

    @Test
    public void sendAttachFileMail(){
        mailService.sendAttachFileMail("liushuai152987@qq.com",
                "804982197@qq.com","测试邮件主题","测试邮件内容",new File("C:\\Users\\Administrator\\Desktop\\章广项目文件\\接口对接.doc"));
    }

    @Test
    public void sendMailWithImg(){
        mailService.sendMailWithImg("liushuai152987@qq.com",
                "804982197@qq.com","测试邮件主题","<div>hello,这是一封带图片的邮件："+
                "这是图片1：<div><img src='cid:p01'/></div>"+
                "这是图片2：<div><img src='cid:p02'/></div>"+
                "</div>",new String[]{"C:\\Users\\Administrator\\Desktop\\img\\15592A109-bB-lp.jpg","C:\\Users\\Administrator\\Desktop\\img\\15592B406-LV-lp.jpg"},
                new String[]{"p01","p02"});
    }
}
