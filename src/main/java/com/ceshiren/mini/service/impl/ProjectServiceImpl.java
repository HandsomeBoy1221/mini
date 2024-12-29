package com.ceshiren.mini.service.impl;

import com.ceshiren.mini.common.Constants;
import com.ceshiren.mini.converter.TestCaseConverter;
import com.ceshiren.mini.converter.TestTaskConverter;
import com.ceshiren.mini.dao.TestCaseMapper;
import com.ceshiren.mini.dao.TestTaskCaseRelMapper;
import com.ceshiren.mini.dao.TestTaskMapper;
import com.ceshiren.mini.dto.OperateJenkinsJobDTO;
import com.ceshiren.mini.dto.TestCaseDTO;
import com.ceshiren.mini.dto.TestTaskAddTDO;
import com.ceshiren.mini.dto.TestTaskDTO;
import com.ceshiren.mini.entity.TestCase;
import com.ceshiren.mini.entity.TestTask;
import com.ceshiren.mini.entity.TestTaskCaseRel;
import com.ceshiren.mini.service.JenkinsService;
import com.ceshiren.mini.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private TestCaseConverter testCaseConverter;
    private TestTaskConverter testTaskConverter;
    private TestCaseMapper testCaseMapper;
    private TestTaskMapper testTaskMapper;
    private TestTaskCaseRelMapper testTaskCaseRelMapper;
    private JenkinsService jenkinsService;

    @Autowired
    public void setJenkinsService(JenkinsService jenkinsService) {
        this.jenkinsService = jenkinsService;
    }

    @Autowired
    public void setTestTaskCaseRelMapper(TestTaskCaseRelMapper testTaskCaseRelMapper) {
        this.testTaskCaseRelMapper = testTaskCaseRelMapper;
    }

    @Autowired
    public void setTestTaskMapper(TestTaskMapper testTaskMapper) {
        this.testTaskMapper = testTaskMapper;
    }

    @Autowired
    public void setTestTaskConverter(TestTaskConverter testTaskConverter) {
        this.testTaskConverter = testTaskConverter;
    }

    @Autowired
    public void setTestCaseMapper(TestCaseMapper testCaseMapper) {
        this.testCaseMapper = testCaseMapper;
    }

    @Autowired
    public void setTestCaseConverter(TestCaseConverter testCaseConverter) {
        this.testCaseConverter = testCaseConverter;
    }
    @Override
    public int createTestCase(TestCaseDTO testCaseDTO) {
        //1.testCaseDTO转成testCase
        TestCase testCase = testCaseConverter.testCaseDTOForTestCase(testCaseDTO);

        //2.testCase 创建时间、更新时间
        testCase.setCreateTime(new Date());
        testCase.setUpdateTime(new Date());
        testCase.setStatus(1);
        //3.往数据库内添加对应值
        int i = testCaseMapper.insertUseGeneratedKeys(testCase);
        return i;

    }


    @Override
    public TestCaseDTO getTestCase(TestCaseDTO testCaseDTO) {
        //1. testCaseDTO转换为testCase
        TestCase testCase = testCaseConverter.testCaseDTOForTestCase(testCaseDTO);
        //2. Mapper查找具体的testCase
        testCase = testCaseMapper.selectOne(testCase);

        //3. testCase转换为testCaseDTO
        testCaseDTO = testCaseConverter.testCaseForTestCaseDTO(testCase);

        return testCaseDTO;
    }

    @Override
    public List<TestCaseDTO> getTestCaseList() {
        TestCase testCase = new TestCase();
        testCase.setStatus(1);
        List<TestCase> testCaseList = testCaseMapper.select(testCase);
//      List<TestCase> testCaseList = testCaseMapper.selectAll();
        List<TestCaseDTO> testCaseDTOList = testCaseConverter.testCaseListForTestCaseDTOList(testCaseList);
        return testCaseDTOList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createTask(TestTaskAddTDO testTaskAddTDO) {
        //用例IDlist [1,2]
        List<Integer> testCaseListId = testTaskAddTDO.getTestCaseListId();
        //[1,2] 转换为 1,2,3,4
        String testCaseIds = testCaseListId
                .stream()
                //把每个Interger对象转换为字符串
                .map(String::valueOf)
                //1,2,3,4,5
                .collect(Collectors.joining(","));

        //ids – 如 "1,2,3,4"
        List<TestCase> testCases = testCaseMapper.selectByIds(testCaseIds);
        System.out.println(testCases);
        System.out.println(Objects.nonNull(testCases));
        System.out.println(testCases.size() > 0);
        if(Objects.nonNull(testCases) && testCases.size() > 0){
            TestTask testTask = testTaskConverter.testTaskAddDTOForTestTask(testTaskAddTDO);
            System.out.println(testTaskAddTDO);
            System.out.println(testTask);
            testTask.setStatus(Constants.STATUS_ONE);
            testTask.setCreateTime(new Date());
            testTask.setUpdateTime(new Date());
            //默认要执行的命令 pwd
            //StringBuilder sb = new StringBuilder();
            //mvn -v mvn clean test
            //默认要执行的命令为 testcase对象中的用例数据值,拼接 并且前面加上 -Dtest=
            String command = "-Dtest=";
            String commandCase = "";
            int size = testCases.size();
            for (int i = 0; i < size; i++) {
                commandCase += testCases.get(i).getCaseData();
                if(i != size-1){
                    commandCase += ",";
                }
            }
            System.out.println("插入的任务默认命令为：");
            System.out.println(command + commandCase);
            testTask.setTestCommand(command + commandCase);

            System.out.println(testTask);

            int i = testTaskMapper.insertUseGeneratedKeys(testTask);
            //测试任务与测试用例对应关系添加 testTaskCaseRel
            List<TestTaskCaseRel> testTaskCaseRelList = new ArrayList<>();
            //根据查询出来的testcase 的ID插入测试任务对应的用例ID
            testCases.forEach(testCase -> {
                TestTaskCaseRel testTaskCaseRel = new TestTaskCaseRel();
                testTaskCaseRel.setTaskId(testTask.getId());
                testTaskCaseRel.setCaseId(testCase.getId());
                testTaskCaseRelList.add(testTaskCaseRel);
            });
            System.out.println(testTaskCaseRelList);
            //数据库的批量插入
            //一个测试任务关联了几条有效的测试用例
            int insertList = testTaskCaseRelMapper.insertList(testTaskCaseRelList);
            return insertList;
        }
        return 0;
    }

    @Override
    public TestTaskDTO getTestTask(TestTaskDTO testTaskDTO) {
        TestTask testTask = testTaskConverter.testTaskDTOForTestTask(testTaskDTO);
        TestTaskDTO taskDTO = getTestTaskDTO(testTask);
        return taskDTO;
    }

    private TestTaskDTO getTestTaskDTO(TestTask testTask) {
        //查看任务详情
        testTask = testTaskMapper.selectOne(testTask);
        if(Objects.isNull(testTask)){
            return null;
        }
        System.out.println("查找出来的任务详情内容");
        System.out.println(testTask);
        //去关联表中查看用例列表ID
        Example testTaskCaseRel = new Example(TestTaskCaseRel.class);
        Example.Criteria criteria = testTaskCaseRel.createCriteria();
        //声明的实体类属性
        criteria.andEqualTo("taskId", testTask.getId());
        List<TestTaskCaseRel> testTaskCaseRels = testTaskCaseRelMapper.selectByExample(testTaskCaseRel);
        //每个测试任务对应的用例ID列表
        System.out.println(testTaskCaseRels);
        //获取当前任务的所有用例ID
        List<Integer> caseIdList = new ArrayList<>();
        testTaskCaseRels.forEach(testTaskCaseRel1 -> caseIdList.add(testTaskCaseRel1.getCaseId()));
        //任务拼接
        TestTaskDTO taskDTO = testTaskConverter.testTaskForTestTaskDTO(testTask);
        taskDTO.setTestCaseListId(caseIdList);
        return taskDTO;
    }

    @Override
    public List<TestTaskDTO> getTestTaskList() {
        List<TestTaskDTO> taskDTOList = new ArrayList<>();
        List<TestTask> testTaskList = testTaskMapper.selectAll();
        System.out.println(testTaskList);
        if(Objects.isNull(testTaskList))
            return null;
        testTaskList.forEach(testTask -> {
            TestTaskDTO taskDTO = getTestTaskDTO(testTask);
            taskDTOList.add(taskDTO);
        });
        System.out.println(taskDTOList);
        return taskDTOList;
    }

    @Override
    public TestTaskDTO runTask(TestTaskDTO testTaskDTO) {
        //testTask
        TestTask testTask = testTaskConverter.testTaskDTOForTestTask(testTaskDTO);

        //1.查看当前任务是否存在
        testTask = testTaskMapper.selectOne(testTask);

        if(Objects.isNull(testTask))
            return null;

        //2.调用Jenkins接口
        OperateJenkinsJobDTO operateJenkinsJobDTO = jenkinsService.runTask(testTask);
        testTask.setStatus(Constants.STATUS_THREE);
        testTask.setUpdateTime(new Date());
        testTask.setBuildUrl(operateJenkinsJobDTO.getJenkinsUrl());
        testTask.setJobId(operateJenkinsJobDTO.getJobId());

        testTaskMapper.updateByPrimaryKeySelective(testTask);
        System.out.println(testTask);
        return testTaskConverter.testTaskForTestTaskDTO(testTask);
    }

    @Override
    public int updateTask(TestTaskDTO testTaskDTO) {
        //1.testTaskDTO 转为testTask
        TestTask testTask = testTaskConverter.testTaskDTOForTestTask(testTaskDTO);
        //状态更改为执行中
        testTask.setStatus(2);

        int update = testTaskMapper.updateByPrimaryKeySelective(testTask);
        return update;
    }

    @Override
    public String getReport(Integer taskId) {
        //根据任务ID查找到任务对象
        TestTask testTask = new TestTask();
        testTask.setId(taskId);
        testTask = testTaskMapper.selectOne(testTask);
        System.out.println(testTask);
        String url = testTask.getBuildUrl() + testTask.getJobId() + "/allure";
        return url;
    }

    @Override
    public int deleteTestCase(TestCaseDTO testCaseDTO) {
        //更新testcase的status为0
        TestCase testCase = testCaseConverter.testCaseDTOForTestCase(testCaseDTO);
        testCase = testCaseMapper.selectOne(testCase);
        testCase.setStatus(0);
        testCase.setUpdateTime(new Date());
        System.out.println(testCase);
        int update = testCaseMapper.updateByPrimaryKey(testCase);
        return update;
    }

    @Override
    public int updateTestCase(TestCaseDTO testCaseDTO) {
        //更新testcase的status为0
        TestCase testCase = testCaseConverter.testCaseDTOForTestCase(testCaseDTO);
        TestCase getTestCase = new TestCase();
        getTestCase.setId(testCase.getId());
        getTestCase = testCaseMapper.selectOne(getTestCase);
        getTestCase.setCaseName(testCase.getCaseName());
        getTestCase.setCaseData(testCase.getCaseData());
        getTestCase.setRemark(testCase.getRemark());
        getTestCase.setUpdateTime(new Date());
        System.out.println(getTestCase);
        int update = testCaseMapper.updateByPrimaryKey(getTestCase);
        return update;
    }

}
