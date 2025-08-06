package sapphire.co.kafkaproducer.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sapphire.co.kafkaproducer.utils.GenericException;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws GenericException {
//        if (true) {
//            throw new GenericException("Fake email sending error");
//        }

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        log.info("📩 Sending email to user: {} %n Subject: {} %n Body: {}", to, subject,  body);
    }
}
