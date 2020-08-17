package com.cjcalmeida.carloan.consumers.proponent;

import com.cjcalmeida.carloan.consumers.domain.Proponent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS)
public class ProponentService {

    private final ProponentRepository repository;

    public ProponentService(ProponentRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(final Proponent newProponent) {
        var oProponent = repository.findByIdentifierOrEmail(newProponent.getIdentifier(), newProponent.getEmail());
        var proponent = oProponent.orElse(newProponent);
        proponent.setName(newProponent.getName());
        proponent.setEmail(newProponent.getEmail());
        repository.save(proponent);
    }
}
