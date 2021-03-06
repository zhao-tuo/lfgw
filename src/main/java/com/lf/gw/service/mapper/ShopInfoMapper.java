package com.lf.gw.service.mapper;

import com.lf.gw.domain.*;
import com.lf.gw.service.dto.ShopInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ShopInfo and its DTO ShopInfoDTO.
 */
@Mapper(componentModel = "spring", uses = {ProjectInfoMapper.class})
public interface ShopInfoMapper extends EntityMapper<ShopInfoDTO, ShopInfo> {

    @Mapping(source = "projectInfo.id", target = "projectInfoId")
    @Mapping(source = "projectInfo.projectName",target = "projectName")
    ShopInfoDTO toDto(ShopInfo shopInfo);

    @Mapping(source = "projectInfoId", target = "projectInfo")
    ShopInfo toEntity(ShopInfoDTO shopInfoDTO);

    default ShopInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setId(id);
        return shopInfo;
    }
}
