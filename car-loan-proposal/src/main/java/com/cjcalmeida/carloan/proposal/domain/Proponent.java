package com.cjcalmeida.carloan.proposal.domain;

import javax.persistence.*;

@Entity
@Table(schema = "proposal", name = "proponent")
public class Proponent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true, nullable = false, updatable = false)
    private String identity;
    @Column(nullable = false)
    private String name;
    @Transient
    private String email;

    /**
     * Only for framework
     */
    @Deprecated
    public Proponent() {
    }

    public Proponent(String identity) {
        this.identity = identity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
