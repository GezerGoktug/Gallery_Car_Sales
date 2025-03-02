package com.goktug.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goktug.models.SaledCar;

@Repository
public interface SaledCarRepository extends JpaRepository<SaledCar, Long> {

}
