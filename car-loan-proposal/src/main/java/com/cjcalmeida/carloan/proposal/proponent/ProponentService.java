package com.cjcalmeida.carloan.proposal.proponent;

import com.cjcalmeida.carloan.proposal.domain.Proponent;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProponentService {

    private final ProponentRepository repository;

    public ProponentService(ProponentRepository repository) {
        this.repository = repository;
    }

    public Optional<Proponent> getProponent(String identity){
        return repository.findByIdentity(identity);
    }

}
