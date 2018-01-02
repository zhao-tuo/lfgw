package com.lf.gw.repository;

import com.lf.gw.domain.ShopInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ShopInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShopInfoRepository extends JpaRepository<ShopInfo, Long> {

}
