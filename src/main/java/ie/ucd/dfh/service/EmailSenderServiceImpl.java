package ie.ucd.dfh.service;

import ie.ucd.dfh.model.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService{

    private JavaMailSender javaMailSender;

    @Async
    public void sendEmail(SimpleMailMessage email){
        javaMailSender.send(email);
    }

    @Override
    public void sendForgotEmail(String email, ConfirmationToken confirmationToken) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Password Reset!");
        mailMessage.setFrom("super.secure.app@gmail.com");
        mailMessage.setText("To complete the password reset process, please click here: "
                +"http://localhost:8443/confirm-reset?token="+confirmationToken.getConfirmationToken());
        sendEmail(mailMessage);
    }
}
