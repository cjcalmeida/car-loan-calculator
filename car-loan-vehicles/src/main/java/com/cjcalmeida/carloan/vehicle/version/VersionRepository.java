package com.cjcalmeida.carloan.vehicle.version;

import com.cjcalmeida.carloan.vehicle.domains.Version;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface VersionRepository extends PagingAndSortingRepository<Version, Integer> {

    Optional<Version> findByPublicId(UUID publicId);

    Iterable<Version> findByModelPublicId(UUID modelPublicId);

}
