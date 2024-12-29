package com.ceshiren.mini.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel("测试任务添加实体类")
@Getter
@Setter
@ToString
public class TestTaskAddTDO {
    //测试任务名称
    @ApiModelProperty(value = "测试任务名称")
    private String name;

    //对应测试用例列表
    @ApiModelProperty(value = "对应测试用例列表",example = "[1,4,99]")
    List<Integer> testCaseListId;
    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

}
