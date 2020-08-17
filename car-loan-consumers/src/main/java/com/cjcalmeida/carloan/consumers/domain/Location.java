package com.cjcalmeida.carloan.consumers.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class Location implements Serializable {

    private static final long serialVersionUID = -7897112205851640918L;

    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String city;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
