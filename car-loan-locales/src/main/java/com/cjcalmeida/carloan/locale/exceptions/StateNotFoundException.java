package com.cjcalmeida.carloan.locale.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "State not found")
public class StateNotFoundException extends Exception {
}
