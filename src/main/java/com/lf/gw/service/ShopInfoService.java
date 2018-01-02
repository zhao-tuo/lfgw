package com.lf.gw.service;

import com.lf.gw.service.dto.ShopInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ShopInfo.
 */
public interface ShopInfoService {

    /**
     * Save a shopInfo.
     *
     * @param shopInfoDTO the entity to save
     * @return the persisted entity
     */
    ShopInfoDTO save(ShopInfoDTO shopInfoDTO);

    /**
     * Get all the shopInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ShopInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" shopInfo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ShopInfoDTO findOne(Long id);

    /**
     * Delete the "id" shopInfo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
