package com.lf.gw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lf.gw.service.ProjectTransactionRecordService;
import com.lf.gw.web.rest.errors.BadRequestAlertException;
import com.lf.gw.web.rest.util.HeaderUtil;
import com.lf.gw.web.rest.util.PaginationUtil;
import com.lf.gw.service.dto.ProjectTransactionRecordDTO;
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
 * REST controller for managing ProjectTransactionRecord.
 */
@RestController
@RequestMapping("/api")
public class ProjectTransactionRecordResource {

    private final Logger log = LoggerFactory.getLogger(ProjectTransactionRecordResource.class);

    private static final String ENTITY_NAME = "projectTransactionRecord";

    private final ProjectTransactionRecordService projectTransactionRecordService;

    public ProjectTransactionRecordResource(ProjectTransactionRecordService projectTransactionRecordService) {
        this.projectTransactionRecordService = projectTransactionRecordService;
    }

    /**
     * POST  /project-transaction-records : Create a new projectTransactionRecord.
     *
     * @param projectTransactionRecordDTO the projectTransactionRecordDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new projectTransactionRecordDTO, or with status 400 (Bad Request) if the projectTransactionRecord has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/project-transaction-records")
    @Timed
    public ResponseEntity<ProjectTransactionRecordDTO> createProjectTransactionRecord(@Valid @RequestBody ProjectTransactionRecordDTO projectTransactionRecordDTO) throws URISyntaxException {
        log.debug("REST request to save ProjectTransactionRecord : {}", projectTransactionRecordDTO);
        if (projectTransactionRecordDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectTransactionRecord cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectTransactionRecordDTO result = projectTransactionRecordService.save(projectTransactionRecordDTO);
        return ResponseEntity.created(new URI("/api/project-transaction-records/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /project-transaction-records : Updates an existing projectTransactionRecord.
     *
     * @param projectTransactionRecordDTO the projectTransactionRecordDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated projectTransactionRecordDTO,
     * or with status 400 (Bad Request) if the projectTransactionRecordDTO is not valid,
     * or with status 500 (Internal Server Error) if the projectTransactionRecordDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/project-transaction-records")
    @Timed
    public ResponseEntity<ProjectTransactionRecordDTO> updateProjectTransactionRecord(@Valid @RequestBody ProjectTransactionRecordDTO projectTransactionRecordDTO) throws URISyntaxException {
        log.debug("REST request to update ProjectTransactionRecord : {}", projectTransactionRecordDTO);
        if (projectTransactionRecordDTO.getId() == null) {
            return createProjectTransactionRecord(projectTransactionRecordDTO);
        }
        ProjectTransactionRecordDTO result = projectTransactionRecordService.save(projectTransactionRecordDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, projectTransactionRecordDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /project-transaction-records : get all the projectTransactionRecords.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of projectTransactionRecords in body
     */
    @GetMapping("/project-transaction-records")
    @Timed
    public ResponseEntity<List<ProjectTransactionRecordDTO>> getAllProjectTransactionRecords(Pageable pageable) {
        log.debug("REST request to get a page of ProjectTransactionRecords");
        Page<ProjectTransactionRecordDTO> page = projectTransactionRecordService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/project-transaction-records");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /project-transaction-records/:id : get the "id" projectTransactionRecord.
     *
     * @param id the id of the projectTransactionRecordDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the projectTransactionRecordDTO, or with status 404 (Not Found)
     */
    @GetMapping("/project-transaction-records/{id}")
    @Timed
    public ResponseEntity<ProjectTransactionRecordDTO> getProjectTransactionRecord(@PathVariable Long id) {
        log.debug("REST request to get ProjectTransactionRecord : {}", id);
        ProjectTransactionRecordDTO projectTransactionRecordDTO = projectTransactionRecordService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(projectTransactionRecordDTO));
    }

    /**
     * DELETE  /project-transaction-records/:id : delete the "id" projectTransactionRecord.
     *
     * @param id the id of the projectTransactionRecordDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/project-transaction-records/{id}")
    @Timed
    public ResponseEntity<Void> deleteProjectTransactionRecord(@PathVariable Long id) {
        log.debug("REST request to delete ProjectTransactionRecord : {}", id);
        projectTransactionRecordService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
