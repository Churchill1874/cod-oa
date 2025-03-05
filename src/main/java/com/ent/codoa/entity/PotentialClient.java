package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.ExploreProgressStatus;
import com.ent.codoa.entity.base.BaseInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("potential_client")
public class PotentialClient extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -2738558104634404618L;

    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("描述相关")
    private String introduce;
    @ApiModelProperty("进展状态")
    private ExploreProgressStatus status;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("合同字符串")
    private String contract;
    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;


}
