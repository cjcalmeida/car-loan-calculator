package com.cjcalmeida.carloan.notification.mail;

import com.cjcalmeida.carloan.notification.domain.Mail;
import io.micrometer.core.instrument.Metrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

@Service
public class MailService {

    private final MailRepository repository;
    private final MailFacade mailFacade;

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    public MailService(MailRepository repository, MailFacade mailFacade) {
        this.repository = repository;
        this.mailFacade = mailFacade;
    }

    public void sendEmail(Mail newMail) throws MessagingException {
        log.info("Processing mail id {}", newMail.getPublicId());
        var mail = repository.findByPublicId(newMail.getPublicId()).orElse(newMail);
        try {

            if (Mail.Status.OK.equals(mail.getStatus())){
                Metrics.counter("notification.email", "status", "error", "reason", "repeated")
                        .increment();
                log.info("Mail already sent");
                return;
            }

            mailFacade.prepareAndSend(mail);

            mail.setStatus(Mail.Status.OK);
            mail.setSentDate(new Date());
            repository.save(mail);
            Metrics.counter("notification.email", "status", "success", "reason", "").increment();
            log.info("Email successfully sent");

        }catch (Exception e) {
            log.error("Error sending email {}", e.getMessage(), e);
            mail.setStatus(Mail.Status.ERROR);
            repository.save(mail);
            Metrics.counter("notification.email", "status", "error", "reason", e.getClass().getSimpleName())
                    .increment();
            throw e;
        }
    }


}
