package com.cjcalmeida.carloan.vehicle.model;

import com.cjcalmeida.carloan.vehicle.domains.Model;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ModelService {

    private ModelRepository modelRepository;

    public ModelService(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
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
    public List<Model> list(UUID brandPublicId) {
        return  Collections.unmodifiableList((List<Model>) modelRepository.findByBrandPublicId(brandPublicId));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Model> get(UUID id) {
        Optional<Model> oModel = modelRepository.findByPublicId(id);
        //Force select
        oModel.ifPresent(Model::getBrand);

        return oModel;
    }
}
