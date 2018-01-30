package com.lf.gw.repository;

import com.lf.gw.domain.MenuData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MenuData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MenuDataRepository extends JpaRepository<MenuData, Long>,JpaSpecificationExecutor<MenuData> {

}
