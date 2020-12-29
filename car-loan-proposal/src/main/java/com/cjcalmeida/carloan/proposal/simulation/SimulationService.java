package com.cjcalmeida.carloan.proposal.simulation;

import com.cjcalmeida.carloan.proposal.domain.Location;
import com.cjcalmeida.carloan.proposal.domain.Proponent;
import com.cjcalmeida.carloan.proposal.domain.Simulation;
import com.cjcalmeida.carloan.proposal.exceptions.LoanConditionsNotSatisfiedException;
import com.cjcalmeida.carloan.proposal.location.LocationService;
import com.cjcalmeida.carloan.proposal.proponent.ProponentService;
import com.cjcalmeida.carloan.proposal.vehicle.VehicleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimulationService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final SimulationRepository repository;
    private final VehicleService vehicleService;
    private final SimulationProducer producer;
    private final ProponentService proponentService;
    private final LocationService locationService;

    public SimulationService(SimulationRepository repository, VehicleService vehicleService, SimulationProducer producer, ProponentService proponentService, LocationService locationService) {
        this.repository = repository;
        this.vehicleService = vehicleService;
        this.producer = producer;
        this.proponentService = proponentService;
        this.locationService = locationService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UUID saveSimulation(final Simulation newSimulation) throws LoanConditionsNotSatisfiedException {
        this.isValid(newSimulation);
        log.info("Simulation is valid");
        var publicId = UUID.randomUUID();
        newSimulation.setPublicId(publicId);
        newSimulation.setCreatedAt(new Date());

        Optional<Proponent> oProponent =  proponentService.getProponent(newSimulation.getProponent().getIdentity());
        Proponent dbProponent = oProponent.orElse(newSimulation.getProponent());
        dbProponent.setEmail(newSimulation.getProponent().getEmail());
        newSimulation.setProponent(dbProponent);

        Optional<Location> oLocation = locationService.findLocation(
                newSimulation.getLocation().getCity(),
                newSimulation.getLocation().getState());
        newSimulation.setLocation(oLocation.orElse(newSimulation.getLocation()));

        repository.save(newSimulation);

        log.info("Emmiting event: SimulationCreated");
        producer.proposalSavedEvent(newSimulation);
        return publicId;
    }

    private void isValid(Simulation simulation) throws LoanConditionsNotSatisfiedException {
        //check installment value = (value - downPayment) / paymentTerm
        var isInstallmentOK = BigDecimal.valueOf(simulation.getLoan().getInstallmentValue()).equals(
                BigDecimal.valueOf(
                        (simulation.getLoan().getVehicleValue() - simulation.getLoan().getDownPayment()) /
                        simulation.getLoan().getPaymentTerm()).setScale(2, RoundingMode.HALF_UP));
        log.debug("Is istallment OK? {}", isInstallmentOK);

        // check if vehicle version exists
        var isVehicleExist = vehicleService.getVehicle(simulation.getVehicle().getId()).isPresent();

        log.debug("Is Vehicle exists? {}", isVehicleExist);

        // check payment terms
        var isPaymentTermsOK = true;

        log.debug("Is Payment Terms ok? {}", isPaymentTermsOK);

        if(!isInstallmentOK || !isVehicleExist || !isPaymentTermsOK) {
            var e = new LoanConditionsNotSatisfiedException();
            e.addCause(isInstallmentOK ? null : "error.installment.not.valid");
            e.addCause(isVehicleExist ? null : "error.vehicle.not.valid");
            e.addCause(isPaymentTermsOK ? null : "error.paymentterms.not.valid");
            log.error(e.getMessage(), e);
            throw e;
        }
    }
}
