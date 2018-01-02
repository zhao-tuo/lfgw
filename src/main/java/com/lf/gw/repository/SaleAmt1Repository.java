package com.lf.gw.repository;

import com.lf.gw.domain.SaleAmt1;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SaleAmt1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaleAmt1Repository extends JpaRepository<SaleAmt1, Long> {

}
