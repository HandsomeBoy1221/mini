package com.ceshiren.mini.converter;


import com.ceshiren.mini.dto.TestCaseDTO;
import com.ceshiren.mini.entity.TestCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Repository;

import java.util.List;

//生成的映射器是一个单例范围的 Spring bean，可以通过以下方式检索@Autowired
@Repository
@Mapper(componentModel = "spring")
public interface TestCaseConverter {
    @Mappings({
            @Mapping(target = "caseName",source = "caseName"),
            @Mapping(target = "caseData",source = "caseData"),
            @Mapping(target = "remark",source = "remark")
    })
    TestCase testCaseDTOForTestCase(TestCaseDTO testCaseDTO);
    TestCaseDTO testCaseForTestCaseDTO(TestCase testCase);
    List<TestCaseDTO> testCaseListForTestCaseDTOList(List<TestCase> testCaseList);
}
