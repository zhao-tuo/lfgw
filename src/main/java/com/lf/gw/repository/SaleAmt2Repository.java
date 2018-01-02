package com.lf.gw.repository;

import com.lf.gw.domain.SaleAmt2;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SaleAmt2 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleAmt2Repository extends JpaRepository<SaleAmt2, Long> {

}
