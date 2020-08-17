package com.cjcalmeida.carloan.consumers.proponent;

import com.cjcalmeida.carloan.consumers.domain.Proponent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface ProponentRepository extends CrudRepository<Proponent, Integer> {

    Optional<Proponent> findByIdentifierOrEmail(String identifier, String email);

}
