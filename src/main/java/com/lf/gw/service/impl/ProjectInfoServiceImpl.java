package com.lf.gw.service.impl;

import com.lf.gw.service.ProjectInfoService;
import com.lf.gw.domain.ProjectInfo;
import com.lf.gw.repository.ProjectInfoRepository;
import com.lf.gw.service.dto.ProjectInfoDTO;
import com.lf.gw.service.mapper.ProjectInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ProjectInfo.
 */
@Service
@Transactional
public class ProjectInfoServiceImpl implements ProjectInfoService{

    private final Logger log = LoggerFactory.getLogger(ProjectInfoServiceImpl.class);

    private final ProjectInfoRepository projectInfoRepository;

    private final ProjectInfoMapper projectInfoMapper;

    public ProjectInfoServiceImpl(ProjectInfoRepository projectInfoRepository, ProjectInfoMapper projectInfoMapper) {
        this.projectInfoRepository = projectInfoRepository;
        this.projectInfoMapper = projectInfoMapper;
    }

    /**
     * Save a projectInfo.
     *
     * @param projectInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProjectInfoDTO save(ProjectInfoDTO projectInfoDTO) {
        log.debug("Request to save ProjectInfo : {}", projectInfoDTO);
        ProjectInfo projectInfo = projectInfoMapper.toEntity(projectInfoDTO);
        projectInfo = projectInfoRepository.save(projectInfo);
        return projectInfoMapper.toDto(projectInfo);
    }

    /**
     * Get all the projectInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProjectInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectInfos");
        return projectInfoRepository.findAll(pageable)
            .map(projectInfoMapper::toDto);
    }

    /**
     * Get one projectInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProjectInfoDTO findOne(Long id) {
        log.debug("Request to get ProjectInfo : {}", id);
        ProjectInfo projectInfo = projectInfoRepository.findOne(id);
        return projectInfoMapper.toDto(projectInfo);
    }

    /**
     * Delete the projectInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectInfo : {}", id);
        projectInfoRepository.delete(id);
    }
}
