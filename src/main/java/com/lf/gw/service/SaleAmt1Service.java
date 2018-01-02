package com.lf.gw.service;

import com.lf.gw.service.dto.SaleAmt1DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SaleAmt1.
 */
public interface SaleAmt1Service {

    /**
     * Save a saleAmt1.
     *
     * @param saleAmt1DTO the entity to save
     * @return the persisted entity
     */
    SaleAmt1DTO save(SaleAmt1DTO saleAmt1DTO);

    /**
     * Get all the saleAmt1S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SaleAmt1DTO> findAll(Pageable pageable);

    /**
     * Get the "id" saleAmt1.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SaleAmt1DTO findOne(Long id);

    /**
     * Delete the "id" saleAmt1.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
