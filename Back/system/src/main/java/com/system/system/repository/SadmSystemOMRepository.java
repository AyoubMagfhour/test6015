package com.system.system.repository;

import com.system.system.jpa.SadmSystemOM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SadmSystemOMRepository extends JpaRepository<SadmSystemOM, Long> {

}

