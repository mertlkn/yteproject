package yteproject.application.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);

        msg.setSubject(subject);
        msg.setText(text);

        javaMailSender.send(msg);
    }

//    public void sendAttachedMessage(String to, String subject, String text, String eventName, String name, String surname, String tcKimlikNo) throws MessagingException, IOException {
//        MimeMessage msg = javaMailSender.createMimeMessage();
//
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//
//        helper.setTo(to);
//        helper.setSubject(subject);
//        byte[] a = new byte[0];
//        helper.addInline("qrcode.png",new File());
//
//
//        javaMailSender.send(msg);
//
//    }
}