package org.example.springpfa.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    private MailSender mailSender;

    public void sendEmail(String emailBody, String Receiver) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("hotel.golden620@gmail.com");
        message.setTo(Receiver);
        message.setSubject("The Admin has Responded to your Request ");
        message.setText(emailBody);
        mailSender.send(message);
        System.out.println("email \n" + emailBody +"\nsent to Receiver :" + Receiver);
    }
}
