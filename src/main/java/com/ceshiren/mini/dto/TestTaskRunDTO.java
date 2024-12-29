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
@ApiModel("测试任务运行实体类")
public class TestTaskRunDTO {
    @ApiModelProperty(value = "测试任务id")
    private Integer id;


    @ApiModelProperty(value = "测试任务执行命令")
    private String testCommand;


    //备注
    @ApiModelProperty(value = "备注")
    private String remark;

}
