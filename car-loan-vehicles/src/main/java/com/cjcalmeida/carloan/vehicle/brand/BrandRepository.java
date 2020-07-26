package com.cjcalmeida.carloan.vehicle.brand;

import com.cjcalmeida.carloan.vehicle.domains.Brand;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface BrandRepository extends PagingAndSortingRepository<Brand, Integer> {

    Optional<Brand> findByPublicId(UUID publicId);

}
