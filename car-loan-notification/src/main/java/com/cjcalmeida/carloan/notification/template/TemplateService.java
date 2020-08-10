package com.cjcalmeida.carloan.notification.template;

import com.cjcalmeida.carloan.notification.domain.Template;
import com.cjcalmeida.carloan.notification.exception.TemplateNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class TemplateService {

    private final TemplateRepository repository;


    public TemplateService(TemplateRepository repository) {
        this.repository = repository;
    }

    public Template getTemplate(String name) throws TemplateNotFoundException {
        return repository.findByName(name).orElseThrow(TemplateNotFoundException::new);
    }

}
