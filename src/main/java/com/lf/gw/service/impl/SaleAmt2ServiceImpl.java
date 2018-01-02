package com.lf.gw.service.impl;

import com.lf.gw.service.SaleAmt2Service;
import com.lf.gw.domain.SaleAmt2;
import com.lf.gw.repository.SaleAmt2Repository;
import com.lf.gw.service.dto.SaleAmt2DTO;
import com.lf.gw.service.mapper.SaleAmt2Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SaleAmt2.
 */
@Service
@Transactional
public class SaleAmt2ServiceImpl implements SaleAmt2Service{

    private final Logger log = LoggerFactory.getLogger(SaleAmt2ServiceImpl.class);

    private final SaleAmt2Repository saleAmt2Repository;

    private final SaleAmt2Mapper saleAmt2Mapper;

    public SaleAmt2ServiceImpl(SaleAmt2Repository saleAmt2Repository, SaleAmt2Mapper saleAmt2Mapper) {
        this.saleAmt2Repository = saleAmt2Repository;
        this.saleAmt2Mapper = saleAmt2Mapper;
    }

    /**
     * Save a saleAmt2.
     *
     * @param saleAmt2DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SaleAmt2DTO save(SaleAmt2DTO saleAmt2DTO) {
        log.debug("Request to save SaleAmt2 : {}", saleAmt2DTO);
        SaleAmt2 saleAmt2 = saleAmt2Mapper.toEntity(saleAmt2DTO);
        saleAmt2 = saleAmt2Repository.save(saleAmt2);
        return saleAmt2Mapper.toDto(saleAmt2);
    }

    /**
     * Get all the saleAmt2S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SaleAmt2DTO> findAll(Pageable pageable) {
        log.debug("Request to get all SaleAmt2S");
        return saleAmt2Repository.findAll(pageable)
            .map(saleAmt2Mapper::toDto);
    }

    /**
     * Get one saleAmt2 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SaleAmt2DTO findOne(Long id) {
        log.debug("Request to get SaleAmt2 : {}", id);
        SaleAmt2 saleAmt2 = saleAmt2Repository.findOne(id);
        return saleAmt2Mapper.toDto(saleAmt2);
    }

    /**
     * Delete the saleAmt2 by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SaleAmt2 : {}", id);
        saleAmt2Repository.delete(id);
    }
}
