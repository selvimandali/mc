package com.ccs.creditcardapply.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccs.creditcardapply.entity.Address;

/*
 * AddressRepository is an interface extending JpaRepository interface for maintaining user defined method for 
 * database manipulations. 
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
