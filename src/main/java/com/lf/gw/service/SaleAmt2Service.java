package com.lf.gw.service;

import com.lf.gw.service.dto.SaleAmt2DTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing SaleAmt2.
 */
public interface SaleAmt2Service {

    /**
     * Save a saleAmt2.
     *
     * @param saleAmt2DTO the entity to save
     * @return the persisted entity
     */
    SaleAmt2DTO save(SaleAmt2DTO saleAmt2DTO);

    /**
     * Get all the saleAmt2S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SaleAmt2DTO> findAll(Pageable pageable);

    /**
     * Get the "id" saleAmt2.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SaleAmt2DTO findOne(Long id);

    /**
     * Delete the "id" saleAmt2.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
