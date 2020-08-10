package com.cjcalmeida.carloan.notification.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "template", schema = "notifications")
public class Template implements Serializable {

    private static final long serialVersionUID = 5389577745420377165L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = false, unique = true, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String file;

    @Column
    private String resources;

    /**
     * Constructor for framework only
     */
    @Deprecated
    public Template() {
    }

    public Template(String name) {
        this.name = name;
        this.file = file;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<String> getResources() {
        if(this.resources != null) {
            return List.of(resources.split(";"));
        }

        return null;
    }

    public void setResources(List<String> resources) {
        if(resources != null) {
            this.resources = String.join(";", resources);
        }
        this.resources = null;
    }
}
