package com.cjcalmeida.carloan.proposal.domain;

public class PaymentTerms {

    private Loan minValues;
    private Loan maxValues;

    public Loan getMinValues() {
        return minValues;
    }

    public void setMinValues(Loan minValues) {
        this.minValues = minValues;
    }

    public Loan getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(Loan maxValues) {
        this.maxValues = maxValues;
    }
}
