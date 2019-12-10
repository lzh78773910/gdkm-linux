package com.gdkm;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinuxApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private JavaMailSenderImpl mailSender;
    private Integer redisExpire = 60 * 1000;
    /**
     * 发送包含简单文本的邮件
     */
    @Test
    public void sendTxtMail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 设置收件人，寄件人
        simpleMailMessage.setTo("78773910@qq.com");//收件人邮箱
        simpleMailMessage.setFrom("lzh421@qq.com");//发送者邮箱
        simpleMailMessage.setSubject("Spring Boot Mail 邮件测试【文本】");
        simpleMailMessage.setText("这里是一段简单文本。");
        redisTemplate.opsForValue().set("lzh421@qq.com","666",redisExpire);
        // 发送邮件
        mailSender.send(simpleMailMessage);
        System.err.println("邮件已发送");
    }

    @Test
    public void redis(){
        String s = (String)redisTemplate.opsForValue().get("lzh421@qq.com");
        System.out.println("00"+s);
    }

}
