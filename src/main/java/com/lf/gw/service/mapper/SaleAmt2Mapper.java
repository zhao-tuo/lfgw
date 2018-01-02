package com.lf.gw.service.mapper;

import com.lf.gw.domain.*;
import com.lf.gw.service.dto.SaleAmt2DTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SaleAmt2 and its DTO SaleAmt2DTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SaleAmt2Mapper extends EntityMapper<SaleAmt2DTO, SaleAmt2> {

    

    

    default SaleAmt2 fromId(Long id) {
        if (id == null) {
            return null;
        }
        SaleAmt2 saleAmt2 = new SaleAmt2();
        saleAmt2.setId(id);
        return saleAmt2;
    }
}
