package com.cjcalmeida.carloan.consumers.proponent;

import com.cjcalmeida.carloan.consumers.domain.Location;
import com.cjcalmeida.carloan.consumers.domain.Proponent;
import com.cjcalmeida.carloan.core.events.LoanEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.Date;

@EnableBinding(Sink.class)
class ProponentListener {

    private final ProponentService service;
    private final Logger log = LoggerFactory.getLogger(ProponentListener.class);

    public ProponentListener(ProponentService service) {
        this.service = service;
    }

    @StreamListener(value = Sink.INPUT)
    public void onMessage(@Valid LoanEvent<ProposalSavedEnvelope> event) {
        log.info(event.printMetadata());

        var proponentSchema = event.getData().consumer;
        Proponent proponent = new Proponent();
        proponent.setEmail(proponentSchema.email);
        proponent.setName(proponentSchema.name);
        proponent.setIdentifier(proponentSchema.identifier);
        proponent.setCreatedAt(new Date());

        var locationSchema = proponentSchema.location;
        Location location = new Location();
        location.setCity(locationSchema.city);
        location.setState(locationSchema.state);
        proponent.setLocation(location);

        service.save(proponent);
    }

    static class ProposalSavedEnvelope {
        @NotNull
        public ProponentSchema consumer;
    }

    static class ProponentSchema {

        @NotNull
        public String identifier;
        @Email
        @NotNull
        public String email;
        @NotNull
        @Min(5)
        public String name;
        @NotNull
        public ProponentLocationSchema location;

    }

    static class ProponentLocationSchema {

        @NotNull
        @Size(min = 2, max = 2)
        public String state;
        @NotNull
        public String city;
    }
}
