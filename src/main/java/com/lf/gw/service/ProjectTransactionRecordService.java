package com.lf.gw.service;

import com.lf.gw.service.dto.ProjectTransactionRecordDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProjectTransactionRecord.
 */
public interface ProjectTransactionRecordService {

    /**
     * Save a projectTransactionRecord.
     *
     * @param projectTransactionRecordDTO the entity to save
     * @return the persisted entity
     */
    ProjectTransactionRecordDTO save(ProjectTransactionRecordDTO projectTransactionRecordDTO);

    /**
     * Get all the projectTransactionRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProjectTransactionRecordDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projectTransactionRecord.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProjectTransactionRecordDTO findOne(Long id);

    /**
     * Delete the "id" projectTransactionRecord.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
