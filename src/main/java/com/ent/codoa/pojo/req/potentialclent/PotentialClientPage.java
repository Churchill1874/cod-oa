package com.ent.codoa.pojo.req.potentialclent;

import com.ent.codoa.common.constant.enums.ExploreProgressStatus;
import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PotentialClientPage extends PageBase implements Serializable {
    private static final long serialVersionUID = 1559431355296817158L;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述相关")
    private String introduce;

    @ApiModelProperty("进展状态")
    private ExploreProgressStatus status;

    @ApiModelProperty("备注")
    private String remark;

}
