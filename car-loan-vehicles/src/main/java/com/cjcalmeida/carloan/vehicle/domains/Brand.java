package com.cjcalmeida.carloan.vehicle.domains;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "brand", schema = "vehicles")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, updatable = false, unique = true)
    private UUID publicId;
    @Column(nullable = false)
    private String name;
    @Column
    private String shortDescription;

    @Deprecated
    public Brand() {
        //Only for framework
    }

    public Brand(Integer id, UUID publicId) {
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        if (!id.equals(brand.id)) return false;
        return publicId.equals(brand.publicId);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
