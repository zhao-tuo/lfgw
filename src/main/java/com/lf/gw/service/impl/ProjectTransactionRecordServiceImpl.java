package com.lf.gw.service.impl;

import com.lf.gw.service.ProjectTransactionRecordService;
import com.lf.gw.domain.ProjectTransactionRecord;
import com.lf.gw.repository.ProjectTransactionRecordRepository;
import com.lf.gw.service.dto.ProjectTransactionRecordDTO;
import com.lf.gw.service.mapper.ProjectTransactionRecordMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ProjectTransactionRecord.
 */
@Service
@Transactional
public class ProjectTransactionRecordServiceImpl implements ProjectTransactionRecordService{

    private final Logger log = LoggerFactory.getLogger(ProjectTransactionRecordServiceImpl.class);

    private final ProjectTransactionRecordRepository projectTransactionRecordRepository;

    private final ProjectTransactionRecordMapper projectTransactionRecordMapper;

    public ProjectTransactionRecordServiceImpl(ProjectTransactionRecordRepository projectTransactionRecordRepository, ProjectTransactionRecordMapper projectTransactionRecordMapper) {
        this.projectTransactionRecordRepository = projectTransactionRecordRepository;
        this.projectTransactionRecordMapper = projectTransactionRecordMapper;
    }

    /**
     * Save a projectTransactionRecord.
     *
     * @param projectTransactionRecordDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectTransactionRecordDTO save(ProjectTransactionRecordDTO projectTransactionRecordDTO) {
        log.debug("Request to save ProjectTransactionRecord : {}", projectTransactionRecordDTO);
        ProjectTransactionRecord projectTransactionRecord = projectTransactionRecordMapper.toEntity(projectTransactionRecordDTO);
        projectTransactionRecord = projectTransactionRecordRepository.save(projectTransactionRecord);
        return projectTransactionRecordMapper.toDto(projectTransactionRecord);
    }

    /**
     * Get all the projectTransactionRecords.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectTransactionRecordDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectTransactionRecords");
        return projectTransactionRecordRepository.findAll(pageable)
            .map(projectTransactionRecordMapper::toDto);
    }

    /**
     * Get one projectTransactionRecord by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    //@Transactional(readOnly = true)
    public ProjectTransactionRecordDTO findOne(Long id) {
        log.debug("Request to get ProjectTransactionRecord : {}", id);
        ProjectTransactionRecord projectTransactionRecord = projectTransactionRecordRepository.findOne(id);
        return projectTransactionRecordMapper.toDto(projectTransactionRecord);
    }

    /**
     * Delete the projectTransactionRecord by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectTransactionRecord : {}", id);
        projectTransactionRecordRepository.delete(id);
    }
}
