package com.cjcalmeida.carloan.vehicle.model;

import com.cjcalmeida.carloan.vehicle.domains.Model;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface ModelRepository extends PagingAndSortingRepository<Model, Integer> {

    Optional<Model> findByPublicId(UUID publicId);

    Iterable<Model> findByBrandPublicId(UUID publicId);
}
