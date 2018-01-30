package com.lf.gw.service.mapper;

import com.lf.gw.domain.*;
import com.lf.gw.service.dto.MenuDataDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MenuData and its DTO MenuDataDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MenuDataMapper extends EntityMapper<MenuDataDTO, MenuData> {

    @Mapping(source = "parent.id", target = "parentId")
    MenuDataDTO toDto(MenuData menuData); 

    @Mapping(source = "parentId", target = "parent")
    MenuData toEntity(MenuDataDTO menuDataDTO);

    default MenuData fromId(Long id) {
        if (id == null) {
            return null;
        }
        MenuData menuData = new MenuData();
        menuData.setId(id);
        return menuData;
    }
}
