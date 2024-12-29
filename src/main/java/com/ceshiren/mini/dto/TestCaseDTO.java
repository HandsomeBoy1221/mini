package com.ceshiren.mini.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@ApiModel("测试用例实体类")
public class TestCaseDTO {
    @ApiModelProperty(value = "测试用例id")
    private Integer id;

    //测试用例名称
    @ApiModelProperty(value = "测试用例名称")
    private String caseName;
    //测试用例数据
    @ApiModelProperty(value = "测试用例数据")
    private String caseData;
    //备注
    @ApiModelProperty(value = "测试用例备注")
    private String remark;

}
