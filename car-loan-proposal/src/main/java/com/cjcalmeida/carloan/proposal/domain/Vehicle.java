package com.cjcalmeida.carloan.proposal.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "vehicle", catalog = "proposal")
public class Vehicle {

    @Id
    private UUID id;
    @Column(nullable = false)
    private Double marketValue;

    @Deprecated
    public Vehicle() {}

    public Vehicle(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }
}
