package com.cjcalmeida.carloan.proposal.loan;

import com.cjcalmeida.carloan.proposal.domain.Location;
import com.cjcalmeida.carloan.proposal.domain.PaymentTerms;
import com.cjcalmeida.carloan.proposal.domain.Proponent;
import com.cjcalmeida.carloan.proposal.exceptions.VehicleNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/v1/proposal/loan")
class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment terms with max and min values", content = {
                    @Content(schema = @Schema(implementation = PaymentTermsOptionsResource.class))
            })
    })
    public ResponseEntity<PaymentTermsOptionsResource> getPaymentTerms(@RequestBody @Valid SimulationResource simulation) throws VehicleNotFoundException {

        Location location = new Location();
        location.setCity(simulation.location.city);
        location.setState(simulation.location.state);

        Proponent proponent = new Proponent(simulation.proponentIdentity);

        var terms = loanService.getPaymentTerms(proponent, simulation.vehicleVersionId, location);

        return ResponseEntity.ok(toResouce(terms));
    }

    private PaymentTermsOptionsResource toResouce(PaymentTerms terms){
        PaymentTermsOptionsResource optionsResource = new PaymentTermsOptionsResource();

        var maxTerms = terms.getMaxValues();
        PaymentTermsResource maxTermsResource = new PaymentTermsResource();
        maxTermsResource.downPaymentValue = maxTerms.getDownPayment();
        maxTermsResource.paymentTermValue = maxTerms.getPaymentTerm();
        maxTermsResource.taxValue = maxTerms.getTaxValues();
        maxTermsResource.vehicleValue = maxTerms.getVehicleValue();
        optionsResource.maxValues = maxTermsResource;

        var minTerms = terms.getMinValues();
        PaymentTermsResource minTermsResource = new PaymentTermsResource();
        minTermsResource.downPaymentValue = minTerms.getDownPayment();
        minTermsResource.paymentTermValue = minTerms.getPaymentTerm();
        minTermsResource.taxValue = minTerms.getTaxValues();
        minTermsResource.vehicleValue = minTerms.getVehicleValue();
        optionsResource.minValues = minTermsResource;

        return optionsResource;
    }

    static class SimulationResource {
        @NotNull
        public UUID vehicleVersionId;
        @NotNull
        public String proponentIdentity;
        public LocationResource location;
    }

    static class LocationResource {
        public String city;
        public String state;
    }

    static class PaymentTermsOptionsResource {
        public PaymentTermsResource minValues;
        public PaymentTermsResource maxValues;
    }

    static class PaymentTermsResource {
        public Double vehicleValue;
        public Double downPaymentValue;
        public Integer paymentTermValue;
        public Double taxValue;
    }
}
