package com.cjcalmeida.carloan.notification.mail.listener;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

interface MailBinding {

    String INPUT = "notification-mail";

    @Input(MailBinding.INPUT)
    SubscribableChannel input();

}
