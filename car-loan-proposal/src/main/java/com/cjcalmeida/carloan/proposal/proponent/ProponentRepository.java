package com.cjcalmeida.carloan.proposal.proponent;

import com.cjcalmeida.carloan.proposal.domain.Proponent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ProponentRepository extends CrudRepository<Proponent, Integer> {

    Optional<Proponent> findByIdentity(String identity);
}
