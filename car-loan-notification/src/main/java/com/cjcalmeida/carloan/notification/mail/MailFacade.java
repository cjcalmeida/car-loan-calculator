package com.cjcalmeida.carloan.notification.mail;

import com.cjcalmeida.carloan.notification.domain.Mail;
import com.cjcalmeida.carloan.notification.domain.Template;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Component
class MailFacade {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    public MailFacade(SpringTemplateEngine templateEngine,
                      JavaMailSender mailSender,
                      ResourceLoader resourceLoader) {
        this.templateEngine = templateEngine;
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }

    public void prepareAndSend(Mail mail) throws MessagingException {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(mail.getParams());
        Template mailTemplate = mail.getTemplate();
        String htmlBody = templateEngine.process(mailTemplate.getFile(), thymeleafContext);

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(mail.getDestination());
        helper.setSubject(mail.getSubject());
        helper.setText(htmlBody, true);
        this.addInlineResources(mail.getTemplate().getResources(), helper);

        mailSender.send(message);
    }

    private void addInlineResources(List<String> resources, MimeMessageHelper helper) throws MessagingException {
        if(resources == null) return;

        for (int i = 0; i < resources.size(); i++) {
            String path = resources.get(i);
            helper.addInline(String.format("attachment_%d.%s",i+1, path.split("\\.")[1]),
                    resourceLoader.getResource(path));
        }

    }

}
