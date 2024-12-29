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

import static com.ceshiren.mini.util.ResultCode.PARAMETER_ERROR;
import static com.ceshiren.mini.util.ResultCode.PARAMETER_NOT_EXIST;

@RestController
//@RequestMapping("project")
public class TestTaskController {


    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }


    //测试任务的创建
    @ApiOperation(value = "测试任务的创建")
    @PostMapping("/task")
    public R createTask(@RequestBody TestTaskAddTDO testTaskAddTDO){
        //testTaskAddTDO 不能为空
        if(Objects.isNull(testTaskAddTDO)){
            return R.error(PARAMETER_ERROR).message("测试任务为空");
        }
        //name 测试任务名称 不能为空
        if(!StringUtils.hasText(testTaskAddTDO.getName())){
            return R.error(PARAMETER_ERROR).message("测试任务名称不能为空");
        }
        //[1,4,5]
        List<Integer> testCaseListId = testTaskAddTDO.getTestCaseListId();
        int size = testCaseListId.size();//测试用例的个数
        if(0 == size || Objects.isNull(testCaseListId)){
            return R.error(PARAMETER_ERROR).message("用例列表不能为空");
        }
        //一个任务关联了几条测试用例的个数
        int insertTaskNum = projectService.createTask(testTaskAddTDO);
        if(insertTaskNum > 0){
            if(insertTaskNum == size)
                return R.ok().data(testTaskAddTDO).message("当前测试任务的所有测试用例关联成功");
            else
                return R.ok().data(testTaskAddTDO)
                        .message("当前测试任务需要关联" + size +"个测试用例,成功关联了" + insertTaskNum + "个测试用例");

        }
        return R.error(PARAMETER_ERROR).data(testTaskAddTDO);
    }

    @ApiOperation(value = "根据ID获取测试任务")
    @GetMapping("task/data/{taskId}")
    public R getTestTask(@PathVariable Integer taskId){
        TestTaskDTO testTaskDTO = new TestTaskDTO();
        testTaskDTO.setId(taskId);
        testTaskDTO = projectService.getTestTask(testTaskDTO);
        //返回的实体类如果是空，null  error
        if(Objects.isNull(testTaskDTO))
            return R.error(PARAMETER_NOT_EXIST).message("任务不存在");
        return R.ok().data(testTaskDTO);
    }


    @GetMapping("task/list")
    @ApiOperation(value = "获取测试任务列表")
    public R getTestTaskList(){

        List<TestTaskDTO> testTaskDTOS = projectService.getTestTaskList();
        //返回的实体类如果是空，null  error
        if(0 == testTaskDTOS.size() ||Objects.isNull(testTaskDTOS))
            return R.error(PARAMETER_NOT_EXIST).message("任务不存在");
        return R.ok().data(testTaskDTOS);
    }
    //任务状态的更改
    @PostMapping("task/update")
    @ApiOperation(value = "测试任务状态的更改")
    public R updateTask(@RequestBody TestTaskDTO testTaskDTO) {
        int i = projectService.updateTask(testTaskDTO);
        testTaskDTO = projectService.getTestTask(testTaskDTO);
        if(i < 0)
            return R.error(PARAMETER_NOT_EXIST).message("任务状态更改失败");
        System.out.println(testTaskDTO);
        return R.ok().data(testTaskDTO);

    }
    //任务执行
    @PostMapping("run")
    @ApiOperation(value = "执行测试任务")
    public R runTask(@RequestBody TestTaskRunDTO testTaskRunDTO) {

        //testTaskRunDTO 不能为空
        if(Objects.isNull(testTaskRunDTO)){
            return R.error(PARAMETER_ERROR).message("测试任务为空");
        }
        //id 测试任务id 不能为空
        if(Objects.isNull(testTaskRunDTO.getId())){
            return R.error(PARAMETER_ERROR).message("测试任务ID不能为空");
        }
        TestTaskDTO testTaskDTO = new TestTaskDTO();
        BeanUtils.copyProperties(testTaskRunDTO,testTaskDTO);
        TestTaskDTO taskDTO = projectService.runTask(testTaskDTO);

        return R.ok().data(taskDTO);
    }

    //获取Allure报告页面
    @GetMapping("report")
    @ApiOperation(value = "获取Allure报告")
    public R runTask(@RequestParam Integer taskId) {
        String url = projectService.getReport(taskId);
        return R.ok().data(url);
    }
}
