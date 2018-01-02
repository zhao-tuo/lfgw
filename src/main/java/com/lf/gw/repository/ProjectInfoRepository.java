package com.lf.gw.repository;

import com.lf.gw.domain.ProjectInfo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProjectInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectInfoRepository extends JpaRepository<ProjectInfo, Long> {

}
