package com.cjcalmeida.carloan.notification.mail;

import com.cjcalmeida.carloan.notification.domain.Mail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface MailRepository extends CrudRepository<Mail, Integer> {

    Optional<Mail> findByPublicId(UUID publicId);

}
