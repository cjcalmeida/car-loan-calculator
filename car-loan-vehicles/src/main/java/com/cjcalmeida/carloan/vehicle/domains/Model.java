package com.cjcalmeida.carloan.vehicle.domains;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "model", schema = "vehicles")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, updatable = false, unique = true)
    private UUID publicId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String image;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "brand_id", updatable = false)
    private Brand brand;

    @Deprecated
    public Model() {
        //Only for framework
    }

    public Model(Integer id, UUID publicId) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
    }

    public Model(Integer id, UUID publicId, String name, Brand brand) {
        this.id = id;
        this.publicId = publicId;
        this.name = name;
        this.brand = brand;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Model model = (Model) o;

        if (!id.equals(model.id)) return false;
        return publicId.equals(model.publicId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + publicId.hashCode();
        return result;
    }
}
