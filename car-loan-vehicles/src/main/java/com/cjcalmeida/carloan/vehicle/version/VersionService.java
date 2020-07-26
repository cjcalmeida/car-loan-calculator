package com.cjcalmeida.carloan.vehicle.version;

import com.cjcalmeida.carloan.vehicle.domains.Version;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VersionService {
    
    private VersionRepository versionRepository;

    public VersionService(VersionRepository versionRepository) {
        this.versionRepository = versionRepository;
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
    public List<Version> list(UUID modelPublicId) {
        return  Collections.unmodifiableList((List<Version>) versionRepository.findByModelPublicId(modelPublicId));
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Optional<Version> get(UUID id) {
        Optional<Version> oVersion = versionRepository.findByPublicId(id);

        //Force select
        oVersion.ifPresent(version -> version.getModel().getBrand().getId());

        return oVersion;
    }
    
}
