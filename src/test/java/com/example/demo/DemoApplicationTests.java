package com.example.demo;

import com.example.demo.Component.MailService;
import com.example.demo.pojo.User;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import freemarker.template.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@SpringBootTest
@RunWith(SpringRunner.class)
class DemoApplicationTests {
    @Resource
    MailService mailService;

    @Test
   public void sendTest() {
        mailService.sendSimpMail("3210440242@qq.com","1798667471@qq.com",
                "1798667471@qq.com","测试","333333");
    }


    @Test
    public void sendAttachFileMail() {
        mailService.sendAttachFileMail("3210440242@qq.com","1798667471@qq.com",
                "1798667471@qq.com","测试","文件",new File("D://js默写.xlsx"));
    }

    @Test
    public void sendMailImg(){
        mailService.sendMailImg("3210440242@qq.com","1798667471@qq.com",
                "1798667471@qq.com","测试:" +
                        "<div><img src='cid:p1'/></div>" +
                        "<div><img src='cid:p2'/></div>",
                new String[]{"C:\\Users\\Administrator\\Pictures\\shuihua.jpg","C:\\Users\\Administrator\\Pictures\\stephencruuy.jpg"},
                new String[]{"p1","p2"});
    }
    @Test
    public void sendHtmlMailFreemarker(){
        try {
        Configuration configuration=new Configuration(freemarker.template.Configuration.VERSION_2_3_0);
        ClassLoader loader = DemoApplication.class.getClassLoader();
        configuration.setClassLoaderForTemplateLoading(loader,"ftl");

            Template template = configuration.getTemplate("mailtemplate.ftl");
            StringWriter mail = new StringWriter();
            User user = new User();
            user.setSex(1);
            user.setUsername("龙云");
            template.process(user,mail);
            mailService.sendHtmlMailFreemarker("3210440242@qq.com","1798667471@qq.com",
            "asd",mail.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Resource
    TemplateEngine templateEngine;
    @Test
    public void sendHtmlMailThymeleaf(){
        Context ctx = new Context();
        ctx.setVariable("username","sang");
        ctx.setVariable("sex","男");
        String mail = templateEngine.process("mailtemplate.html",ctx);
        mailService.sendHtmlMailFreemarker("3210440242@qq.com","1798667471@qq.com",
                "asd",mail);
    }

}
