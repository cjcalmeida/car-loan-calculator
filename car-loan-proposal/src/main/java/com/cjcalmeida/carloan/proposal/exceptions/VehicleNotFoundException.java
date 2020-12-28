package com.cjcalmeida.carloan.proposal.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends Exception {

    private static final long serialVersionUID = 7175397790741233493L;
}
