package com.cjcalmeida.carloan.notification.template;

import com.cjcalmeida.carloan.notification.domain.Template;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface TemplateRepository extends CrudRepository<Template, Integer> {

    Optional<Template> findByName(String name);
}
