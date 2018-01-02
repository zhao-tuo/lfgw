package com.lf.gw.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lf.gw.service.ShopInfoService;
import com.lf.gw.web.rest.errors.BadRequestAlertException;
import com.lf.gw.web.rest.util.HeaderUtil;
import com.lf.gw.web.rest.util.PaginationUtil;
import com.lf.gw.service.dto.ShopInfoDTO;
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
 * REST controller for managing ShopInfo.
 */
@RestController
@RequestMapping("/api")
public class ShopInfoResource {

    private final Logger log = LoggerFactory.getLogger(ShopInfoResource.class);

    private static final String ENTITY_NAME = "shopInfo";

    private final ShopInfoService shopInfoService;

    public ShopInfoResource(ShopInfoService shopInfoService) {
        this.shopInfoService = shopInfoService;
    }

    /**
     * POST  /shop-infos : Create a new shopInfo.
     *
     * @param shopInfoDTO the shopInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shopInfoDTO, or with status 400 (Bad Request) if the shopInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shop-infos")
    @Timed
    public ResponseEntity<ShopInfoDTO> createShopInfo(@Valid @RequestBody ShopInfoDTO shopInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ShopInfo : {}", shopInfoDTO);
        if (shopInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new shopInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShopInfoDTO result = shopInfoService.save(shopInfoDTO);
        return ResponseEntity.created(new URI("/api/shop-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shop-infos : Updates an existing shopInfo.
     *
     * @param shopInfoDTO the shopInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shopInfoDTO,
     * or with status 400 (Bad Request) if the shopInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the shopInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shop-infos")
    @Timed
    public ResponseEntity<ShopInfoDTO> updateShopInfo(@Valid @RequestBody ShopInfoDTO shopInfoDTO) throws URISyntaxException {
        log.debug("REST request to update ShopInfo : {}", shopInfoDTO);
        if (shopInfoDTO.getId() == null) {
            return createShopInfo(shopInfoDTO);
        }
        ShopInfoDTO result = shopInfoService.save(shopInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shopInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shop-infos : get all the shopInfos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of shopInfos in body
     */
    @GetMapping("/shop-infos")
    @Timed
    public ResponseEntity<List<ShopInfoDTO>> getAllShopInfos(Pageable pageable) {
        log.debug("REST request to get a page of ShopInfos");
        Page<ShopInfoDTO> page = shopInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/shop-infos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /shop-infos/:id : get the "id" shopInfo.
     *
     * @param id the id of the shopInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shopInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/shop-infos/{id}")
    @Timed
    public ResponseEntity<ShopInfoDTO> getShopInfo(@PathVariable Long id) {
        log.debug("REST request to get ShopInfo : {}", id);
        ShopInfoDTO shopInfoDTO = shopInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(shopInfoDTO));
    }

    /**
     * DELETE  /shop-infos/:id : delete the "id" shopInfo.
     *
     * @param id the id of the shopInfoDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shop-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteShopInfo(@PathVariable Long id) {
        log.debug("REST request to delete ShopInfo : {}", id);
        shopInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
