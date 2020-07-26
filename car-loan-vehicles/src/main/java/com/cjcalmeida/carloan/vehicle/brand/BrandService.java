package com.cjcalmeida.carloan.vehicle.brand;

import com.cjcalmeida.carloan.vehicle.domains.Brand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {

    private BrandRepository repository;

    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void create(){
        throw new RuntimeException("Not implemented");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(){
        throw new RuntimeException("Not implemented");
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void update(){
        throw new RuntimeException("Not implemented");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Brand> list(){
        return  Collections.unmodifiableList((List<Brand>) repository.findAll());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Brand> get(UUID id) {
        return repository.findByPublicId(id);
    }
}
