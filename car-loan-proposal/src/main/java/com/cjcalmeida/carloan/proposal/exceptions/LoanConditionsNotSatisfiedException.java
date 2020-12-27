package com.cjcalmeida.carloan.proposal.exceptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoanConditionsNotSatisfiedException extends Exception{

    private static final long serialVersionUID = -4950975342708680437L;
    private List<String> causes = new ArrayList<>();

    public LoanConditionsNotSatisfiedException() {}

    public void addCauses(String[] causes) {
        this.causes = Arrays.asList(causes);
    }

    public void addCause(String message) {
        if(message != null) {
            this.causes.add(message);
        }
    }

}
