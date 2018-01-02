package com.lf.gw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lf.gw.service.SaleAmt2Service;
import com.lf.gw.web.rest.errors.BadRequestAlertException;
import com.lf.gw.web.rest.util.HeaderUtil;
import com.lf.gw.web.rest.util.PaginationUtil;
import com.lf.gw.service.dto.SaleAmt2DTO;
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
 * REST controller for managing SaleAmt2.
 */
@RestController
@RequestMapping("/api")
public class SaleAmt2Resource {

    private final Logger log = LoggerFactory.getLogger(SaleAmt2Resource.class);

    private static final String ENTITY_NAME = "saleAmt2";

    private final SaleAmt2Service saleAmt2Service;

    public SaleAmt2Resource(SaleAmt2Service saleAmt2Service) {
        this.saleAmt2Service = saleAmt2Service;
    }

    /**
     * POST  /sale-amt-2-s : Create a new saleAmt2.
     *
     * @param saleAmt2DTO the saleAmt2DTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saleAmt2DTO, or with status 400 (Bad Request) if the saleAmt2 has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sale-amt-2-s")
    @Timed
    public ResponseEntity<SaleAmt2DTO> createSaleAmt2(@Valid @RequestBody SaleAmt2DTO saleAmt2DTO) throws URISyntaxException {
        log.debug("REST request to save SaleAmt2 : {}", saleAmt2DTO);
        if (saleAmt2DTO.getId() != null) {
            throw new BadRequestAlertException("A new saleAmt2 cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SaleAmt2DTO result = saleAmt2Service.save(saleAmt2DTO);
        return ResponseEntity.created(new URI("/api/sale-amt-2-s/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sale-amt-2-s : Updates an existing saleAmt2.
     *
     * @param saleAmt2DTO the saleAmt2DTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saleAmt2DTO,
     * or with status 400 (Bad Request) if the saleAmt2DTO is not valid,
     * or with status 500 (Internal Server Error) if the saleAmt2DTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sale-amt-2-s")
    @Timed
    public ResponseEntity<SaleAmt2DTO> updateSaleAmt2(@Valid @RequestBody SaleAmt2DTO saleAmt2DTO) throws URISyntaxException {
        log.debug("REST request to update SaleAmt2 : {}", saleAmt2DTO);
        if (saleAmt2DTO.getId() == null) {
            return createSaleAmt2(saleAmt2DTO);
        }
        SaleAmt2DTO result = saleAmt2Service.save(saleAmt2DTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saleAmt2DTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sale-amt-2-s : get all the saleAmt2S.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saleAmt2S in body
     */
    @GetMapping("/sale-amt-2-s")
    @Timed
    public ResponseEntity<List<SaleAmt2DTO>> getAllSaleAmt2S(Pageable pageable) {
        log.debug("REST request to get a page of SaleAmt2S");
        Page<SaleAmt2DTO> page = saleAmt2Service.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sale-amt-2-s");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sale-amt-2-s/:id : get the "id" saleAmt2.
     *
     * @param id the id of the saleAmt2DTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saleAmt2DTO, or with status 404 (Not Found)
     */
    @GetMapping("/sale-amt-2-s/{id}")
    @Timed
    public ResponseEntity<SaleAmt2DTO> getSaleAmt2(@PathVariable Long id) {
        log.debug("REST request to get SaleAmt2 : {}", id);
        SaleAmt2DTO saleAmt2DTO = saleAmt2Service.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(saleAmt2DTO));
    }

    /**
     * DELETE  /sale-amt-2-s/:id : delete the "id" saleAmt2.
     *
     * @param id the id of the saleAmt2DTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sale-amt-2-s/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaleAmt2(@PathVariable Long id) {
        log.debug("REST request to delete SaleAmt2 : {}", id);
        saleAmt2Service.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
