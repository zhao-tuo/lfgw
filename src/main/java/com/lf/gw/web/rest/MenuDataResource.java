package com.lf.gw.web.rest;

import com.alibaba.fastjson.JSON;
import com.codahale.metrics.annotation.Timed;
import com.lf.gw.domain.MenuData;
import com.lf.gw.service.MenuDataService;
import com.lf.gw.web.rest.errors.BadRequestAlertException;
import com.lf.gw.web.rest.util.HeaderUtil;
import com.lf.gw.web.rest.util.PaginationUtil;
import com.lf.gw.service.dto.MenuDataDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.json.JSONObject;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing MenuData.
 */
@RestController
@RequestMapping("/api")
public class MenuDataResource {

    private final Logger log = LoggerFactory.getLogger(MenuDataResource.class);

    private static final String ENTITY_NAME = "menuData";

    private final MenuDataService menuDataService;

    public MenuDataResource(MenuDataService menuDataService) {
        this.menuDataService = menuDataService;
    }

    /**
     * POST  /menu-data : Create a new menuData.
     *
     * @param menuDataDTO the menuDataDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new menuDataDTO, or with status 400 (Bad Request) if the menuData has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/menu-data")
    @Timed
    public ResponseEntity<MenuDataDTO> createMenuData(@Valid @RequestBody MenuDataDTO menuDataDTO) throws URISyntaxException {
        log.debug("REST request to save MenuData : {}", menuDataDTO);
        if (menuDataDTO.getId() != null) {
            throw new BadRequestAlertException("A new menuData cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MenuDataDTO result = menuDataService.save(menuDataDTO);
        return ResponseEntity.created(new URI("/api/menu-data/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /menu-data : Updates an existing menuData.
     *
     * @param menuDataDTO the menuDataDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated menuDataDTO,
     * or with status 400 (Bad Request) if the menuDataDTO is not valid,
     * or with status 500 (Internal Server Error) if the menuDataDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/menu-data")
    @Timed
    public ResponseEntity<MenuDataDTO> updateMenuData(@Valid @RequestBody MenuDataDTO menuDataDTO) throws URISyntaxException {
        log.debug("REST request to update MenuData : {}", menuDataDTO);
        if (menuDataDTO.getId() == null) {
            return createMenuData(menuDataDTO);
        }
        MenuDataDTO result = menuDataService.save(menuDataDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, menuDataDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /menu-data : get all the menuData.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of menuData in body
     */
    @GetMapping("/menu-data")
    @Timed
    public ResponseEntity<List<MenuDataDTO>> getAllMenuData(Pageable pageable) {
        log.debug("REST request to get a page of MenuData");
        Page<MenuDataDTO> page = menuDataService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/menu-data");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /menu-data : get all the menuData.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of menuData in body
     */
    @GetMapping("/_root/menu-data")
    @Timed
    public ResponseEntity<String> getMenuDataTree() {
        log.debug("REST request to get MenuDataTree");
        List<MenuDataDTO> list = menuDataService.findAll();
        List<MenuDataDTO> treeList = buildTree(list);
        return new ResponseEntity<String>(JSON.toJSONString(treeList), new HttpHeaders(), HttpStatus.OK);
    }

    private List<MenuDataDTO> buildTree(List<MenuDataDTO> list) {
        List<MenuDataDTO> treeList = new ArrayList<>();
        for(MenuDataDTO child : list){
            if(child.getParentId()==null){
                treeList.add(child);
                continue;
            }
            for(MenuDataDTO parent : list){
                if(parent.getId()==child.getParentId()){
                    parent.getChildren().add(child);
                    break;
                }
            }
        }
        return treeList;
    }

    /**
     * GET  /menu-data/:id : get the "id" menuData.
     *
     * @param id the id of the menuDataDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the menuDataDTO, or with status 404 (Not Found)
     */
    @GetMapping("/menu-data/{id}")
    @Timed
    public ResponseEntity<MenuDataDTO> getMenuData(@PathVariable Long id) {
        log.debug("REST request to get MenuData : {}", id);
        MenuDataDTO menuDataDTO = menuDataService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(menuDataDTO));
    }

    /**
     * DELETE  /menu-data/:id : delete the "id" menuData.
     *
     * @param id the id of the menuDataDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/menu-data/{id}")
    @Timed
    public ResponseEntity<Void> deleteMenuData(@PathVariable Long id) {
        log.debug("REST request to delete MenuData : {}", id);
        menuDataService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
