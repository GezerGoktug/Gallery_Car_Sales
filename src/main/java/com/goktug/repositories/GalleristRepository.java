package com.goktug.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goktug.models.Gallerist;

@Repository
public interface GalleristRepository extends JpaRepository<Gallerist, Long> {

}
