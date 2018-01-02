package com.lf.gw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lf.gw.service.ProjectInfoService;
import com.lf.gw.web.rest.errors.BadRequestAlertException;
import com.lf.gw.web.rest.util.HeaderUtil;
import com.lf.gw.web.rest.util.PaginationUtil;
import com.lf.gw.service.dto.ProjectInfoDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProjectInfo.
 */
@RestController
@RequestMapping("/api")
public class ProjectInfoResource {

    private final Logger log = LoggerFactory.getLogger(ProjectInfoResource.class);

    private static final String ENTITY_NAME = "projectInfo";

    private final ProjectInfoService projectInfoService;

    public ProjectInfoResource(ProjectInfoService projectInfoService) {
        this.projectInfoService = projectInfoService;
    }

    /**
     * POST  /project-infos : Create a new projectInfo.
     *
     * @param projectInfoDTO the projectInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectInfoDTO, or with status 400 (Bad Request) if the projectInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-infos")
    @Timed
    public ResponseEntity<ProjectInfoDTO> createProjectInfo(@Valid @RequestBody ProjectInfoDTO projectInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectInfo : {}", projectInfoDTO);
        if (projectInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectInfoDTO result = projectInfoService.save(projectInfoDTO);
        return ResponseEntity.created(new URI("/api/project-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-infos : Updates an existing projectInfo.
     *
     * @param projectInfoDTO the projectInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectInfoDTO,
     * or with status 400 (Bad Request) if the projectInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-infos")
    @Timed
    public ResponseEntity<ProjectInfoDTO> updateProjectInfo(@Valid @RequestBody ProjectInfoDTO projectInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectInfo : {}", projectInfoDTO);
        if (projectInfoDTO.getId() == null) {
            return createProjectInfo(projectInfoDTO);
        }
        ProjectInfoDTO result = projectInfoService.save(projectInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-infos : get all the projectInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectInfos in body
     */
    @GetMapping("/project-infos")
    @Timed
    public ResponseEntity<List<ProjectInfoDTO>> getAllProjectInfos(Pageable pageable) {
        log.debug("REST request to get a page of ProjectInfos");
        Page<ProjectInfoDTO> page = projectInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project-infos/:id : get the "id" projectInfo.
     *
     * @param id the id of the projectInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/project-infos/{id}")
    @Timed
    public ResponseEntity<ProjectInfoDTO> getProjectInfo(@PathVariable Long id) {
        log.debug("REST request to get ProjectInfo : {}", id);
        ProjectInfoDTO projectInfoDTO = projectInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectInfoDTO));
    }

    /**
     * DELETE  /project-infos/:id : delete the "id" projectInfo.
     *
     * @param id the id of the projectInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectInfo(@PathVariable Long id) {
        log.debug("REST request to delete ProjectInfo : {}", id);
        projectInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
