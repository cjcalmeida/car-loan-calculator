package com.cjcalmeida.carloan.proposal.simulation;

import com.cjcalmeida.carloan.proposal.domain.*;
import com.cjcalmeida.carloan.proposal.exceptions.LoanConditionsNotSatisfiedException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.UUID;

@RestController
@RequestMapping("/v1/proposal")
class SimulationController {

    private final SimulationService service;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public SimulationController(SimulationService service) {
        this.service = service;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Simulation ID created", content = {
                    @Content(schema = @Schema(implementation = SimulationResponseResource.class))
            }),
            @ApiResponse(responseCode = "400", description = "Simulation validations failed")
    })
    @PostMapping
    public ResponseEntity<SimulationResponseResource> sendSimulation(@RequestBody @Valid SimulationController.ProposalResource simulation){
        try {
            var id = service.saveSimulation(toDomain(simulation));
            SimulationResponseResource response = new SimulationResponseResource();
            response.simulationId = id;

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (LoanConditionsNotSatisfiedException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.badRequest().build();
        }
    }

    private Simulation toDomain(ProposalResource resource) {
        Simulation simulation = new Simulation();

        var loanResource = resource.loan;
        Loan loan = new Loan();
        loan.setDownPayment(loanResource.downPayment);
        loan.setInstallmentValue(loanResource.monthlyInstallment);
        loan.setPaymentTerm(loanResource.paymentTerm);
        loan.setTaxValues(loanResource.tax);
        loan.setVehicleValue(loanResource.value);
        simulation.setLoan(loan);

        var locResource = resource.location;
        Location location = new Location();
        location.setState(locResource.state);
        location.setCity(locResource.city);
        simulation.setLocation(location);

        var propResource = resource.proponent;
        Proponent proponent = new Proponent(propResource.identity);
        proponent.setName(propResource.name);
        proponent.setEmail(propResource.email);
        simulation.setProponent(proponent);

        Vehicle vehicle = new Vehicle(resource.vehicleVersionId);
        simulation.setVehicle(vehicle);

        return simulation;
    }

    static class ProposalResource {
        @NotNull
        public UUID vehicleVersionId;
        @NotNull
        public LoanResource loan;
        @NotNull
        public ProponentResource proponent;
        @NotNull
        public LocationResource location;
    }

    static class LoanResource {
        @NotNull
        @Positive
        public Double tax;
        @NotNull
        @Positive
        public Double value;
        @NotNull
        @Positive
        public Double monthlyInstallment;
        @NotNull
        @Positive
        public Double downPayment;
        @NotNull
        @Positive
        @Min(1)
        public Integer paymentTerm;
    }

    static class ProponentResource {
        @NotEmpty
        public String identity;
        @NotEmpty
        public String name;
        @NotEmpty
        @Email
        public String email;
    }

    static class LocationResource {
        @NotEmpty
        public String city;
        @NotEmpty
        public String state;
    }

    static class SimulationResponseResource {
        public UUID simulationId;
    }
}
