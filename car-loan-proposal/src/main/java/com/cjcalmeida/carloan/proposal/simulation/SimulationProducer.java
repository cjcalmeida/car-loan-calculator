package com.cjcalmeida.carloan.proposal.simulation;

import com.cjcalmeida.carloan.core.events.LoanEvent;
import com.cjcalmeida.carloan.proposal.domain.Simulation;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EnableBinding(Source.class)
public class SimulationProducer {

    private final Source source;

    public SimulationProducer(Source source) {
        this.source = source;
    }

    public void proposalSavedEvent(Simulation simulation) {
        source.output().send(MessageBuilder.withPayload(toEvent(simulation)).build());
    }

    private LoanEvent<SimulationEnvelope> toEvent(Simulation simulation){

        SimulationSchema simulationSchema = new SimulationSchema();
        simulationSchema.id = simulation.getPublicId();

        var loan = simulation.getLoan();
        simulationSchema.downPayment = loan.getDownPayment();
        simulationSchema.installmentValue = loan.getInstallmentValue();
        simulationSchema.paymentTerm = loan.getPaymentTerm();
        simulationSchema.taxValues = loan.getTaxValues();
        simulationSchema.vehicleValue = loan.getVehicleValue();

        var proponent = simulation.getProponent();
        ConsumerSchema consumerSchema = new ConsumerSchema();
        consumerSchema.email = proponent.getEmail();
        consumerSchema.identifier = proponent.getIdentity();
        consumerSchema.name = proponent.getName();

        var location = simulation.getLocation();
        LocationSchema locationSchema = new LocationSchema();
        locationSchema.city = location.getCity();
        locationSchema.state = location.getState();

        consumerSchema.location = locationSchema;
        simulationSchema.location = locationSchema;
        simulationSchema.proponent = consumerSchema;

        var vehicle = simulation.getVehicle();
        VehicleSchema vehicleSchema = new VehicleSchema();
        vehicleSchema.id = vehicle.getId();
        vehicleSchema.value = vehicle.getMarketValue();

        simulationSchema.vehicleSchema = vehicleSchema;

        NotificationSchema notificationSchema = new NotificationSchema();
        notificationSchema.id = UUID.randomUUID();
        notificationSchema.to = consumerSchema.email;
        notificationSchema.args = new HashMap<>();
        notificationSchema.args.put("username", consumerSchema.name);

        SimulationEnvelope envelope = new SimulationEnvelope();
        envelope.consumer = consumerSchema;
        envelope.notification = notificationSchema;
        envelope.simulation = simulationSchema;

        var event = new LoanEvent<SimulationEnvelope>("cjcalmeida.carloan.proposal.saved");
        event.setData(envelope);
        event.setDatacontenttype("application/json");
        event.setId(simulation.getPublicId());
        event.setTime(new Date());

        return event;
    }

    static class SimulationEnvelope {
        public SimulationSchema simulation;
        public ConsumerSchema consumer;
        public NotificationSchema notification;
    }

    static class SimulationSchema {
        public UUID id;
        public ConsumerSchema proponent;
        public LocationSchema location;
        public VehicleSchema vehicleSchema;
        public Double taxValues;
        public Double vehicleValue;
        public Double installmentValue;
        public Double downPayment;
        public Integer paymentTerm;
    }

    static class ConsumerSchema {
        public String name;
        public String identifier;
        public String email;
        public LocationSchema location;
    }

    static class LocationSchema {
        public String state;
        public String city;
    }

    static class VehicleSchema {
        public UUID id;
        public Double value;
    }

    static class NotificationSchema {
        public UUID id;
        public String to;
        public Map<String, Object> args;
        public String subject = "Proposta Recebida";
        public String type = "mail";
        public String templateName = "proposal-received";
    }

}
