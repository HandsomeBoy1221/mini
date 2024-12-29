package com.ceshiren.mini.service;

import com.ceshiren.mini.dto.TestCaseDTO;
import com.ceshiren.mini.dto.TestTaskAddTDO;
import com.ceshiren.mini.dto.TestTaskDTO;

import java.util.List;

public interface ProjectService {
    int createTestCase(TestCaseDTO testCaseDTO);
    TestCaseDTO getTestCase(TestCaseDTO testCaseDTO);
    List<TestCaseDTO> getTestCaseList();
    int createTask(TestTaskAddTDO testTaskAddTDO);
    TestTaskDTO getTestTask(TestTaskDTO testTaskDTO);
    List<TestTaskDTO> getTestTaskList();
    TestTaskDTO runTask(TestTaskDTO testTaskDTO);
    int updateTask(TestTaskDTO testTaskDTO);
    String getReport(Integer taskId);
    int deleteTestCase(TestCaseDTO testCaseDTO);
    int updateTestCase(TestCaseDTO testCaseDTO);
}
