package com.cjcalmeida.carloan.notification.domain;

import org.hibernate.validator.internal.util.CollectionHelper;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Entity
@Table(name = "email", catalog = "notification")
public class Mail implements Serializable {

    private static final long serialVersionUID = 546656614281216431L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, updatable = false)
    private UUID publicId;

    @Column(nullable = false, updatable = false)
    private String subject;

    @Column(nullable = false, updatable = false)
    private String destination;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(updatable = false, nullable = false)
    private Template template;

    @Temporal(TemporalType.TIMESTAMP)
    private Date sentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Transient
    private Map<String, Object> params;

    /**
     * Constructor for framework only
     */
    @Deprecated
    public Mail() {
    }

    public Mail(UUID publicId, String subject, String destination, Template template) {
        this.publicId = publicId;
        this.subject = subject;
        this.destination = destination;
        this.template = template;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getPublicId() {
        return publicId;
    }

    public String getSubject() {
        return subject;
    }

    public String getDestination() {
        return destination;
    }

    public Template getTemplate() {
        return template;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Map<String, Object> getParams() {
        if(params != null) {
            return CollectionHelper.toImmutableMap(params);
        }
        return null;
    }

    public void setParams(final Map<String, Object> params) {
        if(params != null) {
            this.params = new HashMap<>();
            this.params.putAll(params);
        }
    }

    public static enum Status {
        OK, ERROR
    }
}
