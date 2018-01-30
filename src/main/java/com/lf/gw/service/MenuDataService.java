package com.lf.gw.service;

import com.lf.gw.domain.MenuData;
import com.lf.gw.service.dto.MenuDataDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing MenuData.
 */
public interface MenuDataService {

    /**
     * Save a menuData.
     *
     * @param menuDataDTO the entity to save
     * @return the persisted entity
     */
    MenuDataDTO save(MenuDataDTO menuDataDTO);

    /**
     * Get all the menuData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MenuDataDTO> findAll(Pageable pageable);

    /**
     * Get the "id" menuData.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MenuDataDTO findOne(Long id);

    /**
     * Delete the "id" menuData.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get all the menuData.
     *
     * @return the list of entities
     */
    List<MenuDataDTO> findAll();
}
