package com.cjcalmeida.carloan.notification.mail;

import com.cjcalmeida.carloan.core.events.LoanEvent;
import com.cjcalmeida.carloan.notification.domain.Mail;
import com.cjcalmeida.carloan.notification.exception.TemplateNotFoundException;
import com.cjcalmeida.carloan.notification.template.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@EnableBinding(Sink.class)
class MailListener {

    private final Logger log = LoggerFactory.getLogger(MailListener.class);

    private final TemplateService templateService;
    private final MailService mailService;

    public MailListener(TemplateService templateService, MailService mailService) {
        this.templateService = templateService;
        this.mailService = mailService;
    }

    @StreamListener(Sink.INPUT)
    public void sendMail(@Valid LoanEvent<ProposalSavedEnvelope> event) throws TemplateNotFoundException, MessagingException {
        log.info(event.printMetadata());
        var notificationSchema = event.getData().notification;

        var template = templateService.getTemplate(notificationSchema.templateName);
        log.debug("Using template: {}-{}", template.getId(), template.getName());

        var mail = new Mail(notificationSchema.id, notificationSchema.subject,  notificationSchema.to, template);
        mail.setParams(notificationSchema.args);
        mailService.sendEmail(mail);
    }

    static class ProposalSavedEnvelope {
        public MailSchema notification;
    }

    static class MailSchema {
        @NotNull
        public UUID id;
        @NotNull @NotEmpty
        public String subject;
        @NotNull @Email
        public String to;
        @NotNull @NotEmpty
        public String templateName;
        public Map<String, Object> args;
    }
}
