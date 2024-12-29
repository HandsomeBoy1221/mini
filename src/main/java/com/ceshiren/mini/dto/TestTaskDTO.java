package com.ceshiren.mini.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@ApiModel("测试任务实体类")
public class TestTaskDTO {
    @ApiModelProperty(value = "测试任务id")
    private Integer id;

    //测试任务名称
    @ApiModelProperty(value = "测试任务名称")
    private String name;

    @ApiModelProperty(value = "测试任务command")
    private String testCommand;

    //对应测试用例列表
    @ApiModelProperty(value = "对应测试用例列表",example = "[1,4,99]")
    List<Integer> testCaseListId;
    @ApiModelProperty(value = "测试任务状态",example = "1")
    private Integer status;
    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

}
