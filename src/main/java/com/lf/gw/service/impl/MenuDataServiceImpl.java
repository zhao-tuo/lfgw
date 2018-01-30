package com.lf.gw.service.impl;

import com.lf.gw.service.MenuDataService;
import com.lf.gw.domain.MenuData;
import com.lf.gw.repository.MenuDataRepository;
import com.lf.gw.service.dto.MenuDataDTO;
import com.lf.gw.service.mapper.MenuDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.FetchType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * Service Implementation for managing MenuData.
 */
@Service
@Transactional
public class MenuDataServiceImpl implements MenuDataService{

    private final Logger log = LoggerFactory.getLogger(MenuDataServiceImpl.class);

    private final MenuDataRepository menuDataRepository;

    private final MenuDataMapper menuDataMapper;

    public MenuDataServiceImpl(MenuDataRepository menuDataRepository, MenuDataMapper menuDataMapper) {
        this.menuDataRepository = menuDataRepository;
        this.menuDataMapper = menuDataMapper;
    }

    /**
     * Save a menuData.
     *
     * @param menuDataDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MenuDataDTO save(MenuDataDTO menuDataDTO) {
        log.debug("Request to save MenuData : {}", menuDataDTO);
        MenuData menuData = menuDataMapper.toEntity(menuDataDTO);
        menuData = menuDataRepository.save(menuData);
        return menuDataMapper.toDto(menuData);
    }

    /**
     * Get all the menuData.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MenuDataDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MenuData");
        return menuDataRepository.findAll(pageable)
            .map(menuDataMapper::toDto);
    }

    /**
     * Get one menuData by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MenuDataDTO findOne(Long id) {
        log.debug("Request to get MenuData : {}", id);
        MenuData menuData = menuDataRepository.findOne(id);
        return menuDataMapper.toDto(menuData);
    }

    /**
     * Delete the menuData by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MenuData : {}", id);
        menuDataRepository.delete(id);
    }

    /**
     * Get all the menuData.
     *
     * @return the list of entities
     */
    @Override
    public List<MenuDataDTO> findAll() {
        log.debug("Request to find all menuDataDto");
        return menuDataMapper.toDto(menuDataRepository.findAll());
    }


}
