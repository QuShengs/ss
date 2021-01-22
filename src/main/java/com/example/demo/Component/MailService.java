package com.example.demo.Component;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.File;

@Component
public class MailService {

    @Resource
    JavaMailSender javaMailSender;

    public void sendSimpMail(String from,String to,String cc,
                             String subject,String content){
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        javaMailSender.send(mailMessage);

    }

    public void sendAttachFileMail(String from, String to, String cc,
                                   String subject, String content, File file){
        try {
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mailMessage,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            helper.addAttachment(file.getName(),file);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    public void sendMailImg(String from, String to,
                            String subject, String content, String[] srcPath,String[] resIds){
        if(resIds.length!= srcPath.length){
            System.out.println("发送失败");
            return;
        }

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            for(int i=0;i<srcPath.length;i++){
                FileSystemResource res= new FileSystemResource(new File(srcPath[i]));
                helper.addInline(resIds[i],res);
            }
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("发送失败");
        }

    }

    public void sendHtmlMailFreemarker(String from, String to,
                                       String subject, String content){

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content,true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println("发送失败");
        }


    }

}
