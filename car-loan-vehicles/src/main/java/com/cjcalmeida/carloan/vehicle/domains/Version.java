package com.cjcalmeida.carloan.vehicle.domains;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "version", schema = "vehicles")
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, updatable = false, unique = true)
    private UUID publicId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal value;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "model_id", updatable = false)
    private Model model;

    @Deprecated
    public Version() {
        //Only for framework
    }

    public Version(Integer id, UUID publicId) {
        this.id = id;
        this.publicId = publicId;
    }

    public Integer getId() {
        return id;
    }

    void setId(Integer id) {
        this.id = id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    void setPublicId(UUID publicId) {
        this.publicId = publicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
