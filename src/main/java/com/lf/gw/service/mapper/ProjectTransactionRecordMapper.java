package com.lf.gw.service.mapper;

import com.lf.gw.domain.*;
import com.lf.gw.service.dto.ProjectTransactionRecordDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProjectTransactionRecord and its DTO ProjectTransactionRecordDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectTransactionRecordMapper extends EntityMapper<ProjectTransactionRecordDTO, ProjectTransactionRecord> {

    

    

    default ProjectTransactionRecord fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProjectTransactionRecord projectTransactionRecord = new ProjectTransactionRecord();
        projectTransactionRecord.setId(id);
        return projectTransactionRecord;
    }
}
