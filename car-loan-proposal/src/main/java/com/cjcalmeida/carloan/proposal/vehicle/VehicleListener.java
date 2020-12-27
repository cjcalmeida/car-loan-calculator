package com.cjcalmeida.carloan.proposal.vehicle;

import com.cjcalmeida.carloan.core.events.LoanEvent;
import com.cjcalmeida.carloan.proposal.domain.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@EnableBinding(Sink.class)
class VehicleListener {

    private final VehicleService service;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public VehicleListener(VehicleService service) {
        this.service = service;
    }

    @StreamListener(value = Sink.INPUT)
    public void onVehicleCreated(@Valid LoanEvent<VehicleEnvelope> event) {
        log.info("Receiving event {}", event.getId());
        service.saveVehicle(toDomain(event.getData().vehicle));
        log.info("Event {} processed", event.getId());
    }

    private Vehicle toDomain(VehicleSchema envelope) {
        var vehicle = new Vehicle(envelope.id);
        vehicle.setMarketValue(envelope.marketValue);
        return vehicle;
    }

    static class VehicleEnvelope {
        @NotNull
        public VehicleSchema vehicle;
    }

    static class VehicleSchema {
        @NotNull
        public UUID id;
        @NotNull
        @Min(1)
        public Double marketValue;
    }

}
