package com.lf.gw.service.mapper;

import com.lf.gw.domain.*;
import com.lf.gw.service.dto.SaleAmt1DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SaleAmt1 and its DTO SaleAmt1DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SaleAmt1Mapper extends EntityMapper<SaleAmt1DTO, SaleAmt1> {

    default SaleAmt1 fromId(Long id) {
        if (id == null) {
            return null;
        }
        SaleAmt1 saleAmt1 = new SaleAmt1();
        saleAmt1.setId(id);
        return saleAmt1;
    }
}
