package com.lf.gw.repository;

import com.lf.gw.domain.ProjectTransactionRecord;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProjectTransactionRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectTransactionRecordRepository extends JpaRepository<ProjectTransactionRecord, Long> {

}
