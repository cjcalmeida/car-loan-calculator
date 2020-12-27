package com.cjcalmeida.carloan.proposal.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Loan {

    @Column(nullable = false, updatable = false)
    private Double taxValues;
    @Column(nullable = false, updatable = false)
    private Double vehicleValue;
    @Column(nullable = false, updatable = false)
    private Double installmentValue;
    @Column(nullable = false, updatable = false)
    private Double downPayment;
    @Column(nullable = false, updatable = false)
    private Integer paymentTerm;

    public Double getTaxValues() {
        return taxValues;
    }

    public void setTaxValues(Double taxValues) {
        this.taxValues = taxValues;
    }

    public Double getVehicleValue() {
        return vehicleValue;
    }

    public void setVehicleValue(Double vehicleValue) {
        this.vehicleValue = vehicleValue;
    }

    public Double getInstallmentValue() {
        return installmentValue;
    }

    public void setInstallmentValue(Double installmentValue) {
        this.installmentValue = installmentValue;
    }

    public Double getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(Double downPayment) {
        this.downPayment = downPayment;
    }

    public Integer getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(Integer paymentTerm) {
        this.paymentTerm = paymentTerm;
    }
}
