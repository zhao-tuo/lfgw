package com.lf.gw.service;

import com.lf.gw.service.dto.ProjectInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProjectInfo.
 */
public interface ProjectInfoService {

    /**
     * Save a projectInfo.
     *
     * @param projectInfoDTO the entity to save
     * @return the persisted entity
     */
    ProjectInfoDTO save(ProjectInfoDTO projectInfoDTO);

    /**
     * Get all the projectInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProjectInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projectInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProjectInfoDTO findOne(Long id);

    /**
     * Delete the "id" projectInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
