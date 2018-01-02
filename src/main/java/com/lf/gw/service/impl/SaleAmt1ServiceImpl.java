package com.lf.gw.service.impl;

import com.lf.gw.service.SaleAmt1Service;
import com.lf.gw.domain.SaleAmt1;
import com.lf.gw.repository.SaleAmt1Repository;
import com.lf.gw.service.dto.SaleAmt1DTO;
import com.lf.gw.service.mapper.SaleAmt1Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing SaleAmt1.
 */
@Service
@Transactional
public class SaleAmt1ServiceImpl implements SaleAmt1Service{

    private final Logger log = LoggerFactory.getLogger(SaleAmt1ServiceImpl.class);

    private final SaleAmt1Repository saleAmt1Repository;

    private final SaleAmt1Mapper saleAmt1Mapper;

    public SaleAmt1ServiceImpl(SaleAmt1Repository saleAmt1Repository, SaleAmt1Mapper saleAmt1Mapper) {
        this.saleAmt1Repository = saleAmt1Repository;
        this.saleAmt1Mapper = saleAmt1Mapper;
    }

    /**
     * Save a saleAmt1.
     *
     * @param saleAmt1DTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SaleAmt1DTO save(SaleAmt1DTO saleAmt1DTO) {
        log.debug("Request to save SaleAmt1 : {}", saleAmt1DTO);
        SaleAmt1 saleAmt1 = saleAmt1Mapper.toEntity(saleAmt1DTO);
        saleAmt1 = saleAmt1Repository.save(saleAmt1);
        return saleAmt1Mapper.toDto(saleAmt1);
    }

    /**
     * Get all the saleAmt1S.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SaleAmt1DTO> findAll(Pageable pageable) {
        log.debug("Request to get all SaleAmt1S");
        return saleAmt1Repository.findAll(pageable)
            .map(saleAmt1Mapper::toDto);
    }

    /**
     * Get one saleAmt1 by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SaleAmt1DTO findOne(Long id) {
        log.debug("Request to get SaleAmt1 : {}", id);
        SaleAmt1 saleAmt1 = saleAmt1Repository.findOne(id);
        return saleAmt1Mapper.toDto(saleAmt1);
    }

    /**
     * Delete the saleAmt1 by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SaleAmt1 : {}", id);
        saleAmt1Repository.delete(id);
    }
}
