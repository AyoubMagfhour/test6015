package com.cq.clinique.repository;

import com.cq.clinique.jpa.SadmCliniqueOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SadmCliniqueOMRepository extends JpaRepository<SadmCliniqueOM, Long> {
}
