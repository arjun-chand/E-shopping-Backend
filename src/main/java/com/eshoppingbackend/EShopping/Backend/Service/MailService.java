package com.eshoppingbackend.EShopping.Backend.Service;

import com.eshoppingbackend.EShopping.Backend.Entity.Orders;
import com.eshoppingbackend.EShopping.Backend.Entity.Users;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;
    public void sendEmail() {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        String to = "chandarjun10@gmail.com";
        String sub = "Hi you got your first mail";
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);
            Context context = new Context();
            String htmlContent = templateEngine.process("mailtemplate.html", context);
            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendOrderCreateMail(Users user, Orders order){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        String to = user.getEmail();
        String sub = "Congratulations!! your Order get Successfully placed";
        Context context = new Context();
        context.setVariable("userName", user.getUserName());
        context.setVariable("orderID", order.getOId());
        context.setVariable("estimateDate", order.getEstimateDelivery());
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(sub);
            String htmlContent = templateEngine.process("mailtemplate", context);
            htmlContent = htmlContent.replace("${userName}", user.getUserName());
            htmlContent = htmlContent.replace("${orderID}",order.getOId()+"");
            htmlContent = htmlContent.replace("${estimateDate}", order.getEstimateDelivery()+"");

            mimeMessageHelper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
