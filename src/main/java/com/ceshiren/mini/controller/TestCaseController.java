package com.ceshiren.mini.controller;

import com.ceshiren.mini.dto.TestCaseDTO;
import com.ceshiren.mini.dto.TestTaskAddTDO;
import com.ceshiren.mini.dto.TestTaskDTO;
import com.ceshiren.mini.dto.TestTaskRunDTO;
import com.ceshiren.mini.service.ProjectService;
import com.ceshiren.mini.util.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

import static com.ceshiren.mini.util.ResultCode.*;

@RestController
//@RequestMapping("project")
public class TestCaseController {


    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    //创建测试用例
    @ApiOperation(value = "测试用例的新增")
    @PostMapping("/case")
    public R createTestCase(@RequestBody TestCaseDTO testCaseDTO){
        System.out.println(testCaseDTO);
        //参数是否为空进行判断
        //testCaseDTO 不能为空
        if(Objects.isNull(testCaseDTO)){
            return R.error(PARAMETER_ERROR).message("测试用例为空");
        }
        //caseName  不能为空
        if(!StringUtils.hasText(testCaseDTO.getCaseName())){
            return R.error(PARAMETER_ERROR).message("测试用例名称不能为空");
        }
        //caseData  不能为空
        if(!StringUtils.hasText(testCaseDTO.getCaseData())){
            return R.error(PARAMETER_ERROR).message("测试用例数据不能为空");
        }
        //传入参数正常，可以添加测试用例
//        service.createTestCase
        int insertTestCaseNum = projectService.createTestCase(testCaseDTO);
        return R.ok().data(testCaseDTO).message(insertTestCaseNum + "条测试用例添加成功");
    }

    //根据ID获取具体的用例详情
    @ApiOperation(value = "根据ID获取具体的用例详情")
    @GetMapping("/case/data/{caseId}")
    public R getOneTestCase(@PathVariable Integer caseId){
        TestCaseDTO testCaseDTO = new TestCaseDTO();
        testCaseDTO.setId(caseId);
        TestCaseDTO testCaseDto = projectService.getTestCase(testCaseDTO);
        //返回的实体类如果是空，null  error
        if(Objects.isNull(testCaseDto))
            return R.error(PARAMETER_NOT_EXIST).message("用例不存在");
        return R.ok().data(testCaseDto);
    }

    //查看用例列表
    @ApiOperation(value = "查看用例列表")
    @GetMapping("/case/list")
    public R getTestCaseList(){
        List<TestCaseDTO> testCaseDtoList = projectService.getTestCaseList();
        //返回的实体类如果是空，null  error
        if(0 == testCaseDtoList.size() || Objects.isNull(testCaseDtoList))
            return R.error(PARAMETER_NOT_EXIST).message("没有测试用例");
        return R.ok().data(testCaseDtoList);
    }


    //删除测试用例
    @ApiOperation(value = "测试用例的删除")
    @PostMapping("/case/delete")
    public R deleteTestCase(@RequestBody TestCaseDTO testCaseDTO){
        int deleted = projectService.deleteTestCase(testCaseDTO);
        if(deleted < 0)
            return R.error(DELETEERROR);
        return R.ok().data(testCaseDTO);
    }

    //修改测试用例
    @ApiOperation(value = "测试用例的修改")
    @PostMapping("/case/update")
    public R updateTestCase(@RequestBody TestCaseDTO testCaseDTO){
        int update = projectService.updateTestCase(testCaseDTO);
        if(update < 0)
            return R.error(UPDATERROR);
        return R.ok().data(testCaseDTO);
    }

}
