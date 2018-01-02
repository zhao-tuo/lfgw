package com.lf.gw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lf.gw.service.SaleAmt1Service;
import com.lf.gw.web.rest.errors.BadRequestAlertException;
import com.lf.gw.web.rest.util.HeaderUtil;
import com.lf.gw.web.rest.util.PaginationUtil;
import com.lf.gw.service.dto.SaleAmt1DTO;
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
 * REST controller for managing SaleAmt1.
 */
@RestController
@RequestMapping("/api")
public class SaleAmt1Resource {

    private final Logger log = LoggerFactory.getLogger(SaleAmt1Resource.class);

    private static final String ENTITY_NAME = "saleAmt1";

    private final SaleAmt1Service saleAmt1Service;

    public SaleAmt1Resource(SaleAmt1Service saleAmt1Service) {
        this.saleAmt1Service = saleAmt1Service;
    }

    /**
     * POST  /sale-amt-1-s : Create a new saleAmt1.
     *
     * @param saleAmt1DTO the saleAmt1DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saleAmt1DTO, or with status 400 (Bad Request) if the saleAmt1 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sale-amt-1-s")
    @Timed
    public ResponseEntity<SaleAmt1DTO> createSaleAmt1(@Valid @RequestBody SaleAmt1DTO saleAmt1DTO) throws URISyntaxException {
        log.debug("REST request to save SaleAmt1 : {}", saleAmt1DTO);
        if (saleAmt1DTO.getId() != null) {
            throw new BadRequestAlertException("A new saleAmt1 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleAmt1DTO result = saleAmt1Service.save(saleAmt1DTO);
        return ResponseEntity.created(new URI("/api/sale-amt-1-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sale-amt-1-s : Updates an existing saleAmt1.
     *
     * @param saleAmt1DTO the saleAmt1DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saleAmt1DTO,
     * or with status 400 (Bad Request) if the saleAmt1DTO is not valid,
     * or with status 500 (Internal Server Error) if the saleAmt1DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sale-amt-1-s")
    @Timed
    public ResponseEntity<SaleAmt1DTO> updateSaleAmt1(@Valid @RequestBody SaleAmt1DTO saleAmt1DTO) throws URISyntaxException {
        log.debug("REST request to update SaleAmt1 : {}", saleAmt1DTO);
        if (saleAmt1DTO.getId() == null) {
            return createSaleAmt1(saleAmt1DTO);
        }
        SaleAmt1DTO result = saleAmt1Service.save(saleAmt1DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saleAmt1DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sale-amt-1-s : get all the saleAmt1S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saleAmt1S in body
     */
    @GetMapping("/sale-amt-1-s")
    @Timed
    public ResponseEntity<List<SaleAmt1DTO>> getAllSaleAmt1S(Pageable pageable) {
        log.debug("REST request to get a page of SaleAmt1S");
        Page<SaleAmt1DTO> page = saleAmt1Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-amt-1-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sale-amt-1-s/:id : get the "id" saleAmt1.
     *
     * @param id the id of the saleAmt1DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saleAmt1DTO, or with status 404 (Not Found)
     */
    @GetMapping("/sale-amt-1-s/{id}")
    @Timed
    public ResponseEntity<SaleAmt1DTO> getSaleAmt1(@PathVariable Long id) {
        log.debug("REST request to get SaleAmt1 : {}", id);
        SaleAmt1DTO saleAmt1DTO = saleAmt1Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(saleAmt1DTO));
    }

    /**
     * DELETE  /sale-amt-1-s/:id : delete the "id" saleAmt1.
     *
     * @param id the id of the saleAmt1DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sale-amt-1-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaleAmt1(@PathVariable Long id) {
        log.debug("REST request to delete SaleAmt1 : {}", id);
        saleAmt1Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
