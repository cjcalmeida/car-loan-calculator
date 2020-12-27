package com.cjcalmeida.carloan.proposal.loan;

import com.cjcalmeida.carloan.proposal.domain.Loan;
import com.cjcalmeida.carloan.proposal.domain.Location;
import com.cjcalmeida.carloan.proposal.domain.PaymentTerms;
import com.cjcalmeida.carloan.proposal.domain.Proponent;
import com.cjcalmeida.carloan.proposal.exceptions.VehicleNotFoundException;
import com.cjcalmeida.carloan.proposal.vehicle.VehicleService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Service
public class LoanService {

    private final BigDecimal EXP_TAX = new BigDecimal("0.00753");
    private final Integer MAX_PERCENT_DOWN_PAYMENT = 70;
    private final Integer MIN_PERCENT_DOWN_PAYMENT = 10;
    private final Integer MAX_PAYMENT_TERM = 48;
    private final Integer MIN_PAYMENT_TERM = 6;

    private VehicleService vehicleService;

    public LoanService(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    public PaymentTerms getPaymentTerms(Proponent proponent, UUID vehicleId, Location location) throws VehicleNotFoundException {
        //TODO Use location value to decide tax value
        var vehicle = vehicleService.getVehicle(vehicleId).orElseThrow(VehicleNotFoundException::new);
        var finalId = proponent.getIdentity().substring(proponent.getIdentity().length()-1);

        PaymentTerms paymentTerms = new PaymentTerms();

        Loan minValues = new Loan();
        minValues.setTaxValues(calculateTaxByFinalIdentity(Integer.valueOf(finalId)));
        minValues.setVehicleValue(calculateVehicleValue(vehicle.getMarketValue(), minValues.getTaxValues()));
        minValues.setPaymentTerm(MIN_PAYMENT_TERM);
        minValues.setDownPayment(calculateMinDownPaymentValue(minValues.getVehicleValue()));

        Loan maxValues = new Loan();
        maxValues.setTaxValues(minValues.getTaxValues());
        maxValues.setVehicleValue(minValues.getVehicleValue());
        maxValues.setPaymentTerm(MAX_PAYMENT_TERM);
        maxValues.setDownPayment(calculateMaxDownPaymentValue(maxValues.getVehicleValue()));

        paymentTerms.setMaxValues(maxValues);
        paymentTerms.setMinValues(minValues);

        return paymentTerms;
    }

    private Double calculateVehicleValue(Double vehicleValue, Double tax){
        return BigDecimal.valueOf(vehicleValue*(1+tax))
                .setScale(0, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Double calculateTaxByFinalIdentity(Integer finalId) {
        return EXP_TAX.multiply(BigDecimal.valueOf(finalId)).doubleValue();
    }

    private Double calculateMinDownPaymentValue(Double vehicleValue){
        return BigDecimal.valueOf((vehicleValue * MIN_PERCENT_DOWN_PAYMENT) / 100)
                .setScale(0, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Double calculateMaxDownPaymentValue(Double vehicleValue){
        return BigDecimal.valueOf((vehicleValue * MAX_PERCENT_DOWN_PAYMENT) / 100)
                .setScale(0, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
