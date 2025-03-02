package com.goktug.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.goktug.models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {
    
}
