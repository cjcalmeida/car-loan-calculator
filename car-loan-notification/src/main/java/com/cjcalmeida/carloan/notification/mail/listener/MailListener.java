package com.cjcalmeida.carloan.notification.mail.listener;

import com.cjcalmeida.carloan.notification.domain.Mail;
import com.cjcalmeida.carloan.notification.exception.TemplateNotFoundException;
import com.cjcalmeida.carloan.notification.mail.MailService;
import com.cjcalmeida.carloan.notification.template.TemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@EnableBinding(MailBinding.class)
class MailListener {

    private final Logger log = LoggerFactory.getLogger(MailListener.class);

    private final TemplateService templateService;
    private final MailService mailService;

    public MailListener(TemplateService templateService, MailService mailService) {
        this.templateService = templateService;
        this.mailService = mailService;
    }

    @StreamListener(MailBinding.INPUT)
    public void sendMail(@Valid MailSchema mailSchema) throws TemplateNotFoundException, MessagingException {
        log.info("Receiving command to send mail");

        var template = templateService.getTemplate(mailSchema.templateName);
        log.debug("Using template: {}-{}", template.getId(), template.getName());

        var mail = new Mail(mailSchema.id, mailSchema.subject,  mailSchema.to, template);
        mail.setParams(mailSchema.args);
        mailService.sendEmail(mail);
    }

    static class MailSchema {
        @NotNull
        UUID id;
        @NotNull @NotEmpty
        String subject;
        @NotNull @Email
        String to;
        @NotNull @NotEmpty
        String templateName;
        Map<String, Object> args;

        public void setId(UUID id) {
            this.id = id;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public void setTemplateName(String templateName) {
            this.templateName = templateName;
        }

        public void setArgs(Map<String, Object> args) {
            this.args = args;
        }
    }
}
