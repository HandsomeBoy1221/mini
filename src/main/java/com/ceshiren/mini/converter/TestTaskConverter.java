package com.ceshiren.mini.converter;

import com.ceshiren.mini.dto.TestTaskAddTDO;
import com.ceshiren.mini.dto.TestTaskDTO;
import com.ceshiren.mini.entity.TestTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Repository;

@Repository
@Mapper(componentModel = "spring")
public interface TestTaskConverter {

    TestTask testTaskAddDTOForTestTask(TestTaskAddTDO testTaskAddTDO);

    TestTask testTaskDTOForTestTask(TestTaskDTO testTaskDTO);
    @Mappings({
            @Mapping(target = "testCommand",source = "testCommand"),
            @Mapping(target = "status",source = "status")
    })
    TestTaskDTO testTaskForTestTaskDTO(TestTask testTask);
}
