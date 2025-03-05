package com.ent.codoa.pojo.req.potentialclent;

import com.ent.codoa.common.constant.enums.ExploreProgressStatus;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PotentialClientUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 8144293798886818281L;

    @Length(max = 30, message = "名称30字以内")
    @NotBlank(message = "名称不能为空")
    @ApiModelProperty(value = "名称",required = true)
    private String name;

    @Length(max = 100, message = "描述内容100字以内")
    @ApiModelProperty(value = "描述相关",required = true)
    private String introduce;

    @NotNull(message = "进展状态不能为空")
    @ApiModelProperty(value = "进展状态",required = true)
    private ExploreProgressStatus status;

    @Length(max = 100, message = "备注30字以内")
    @NotBlank(message = "备注不能为空")
    @ApiModelProperty(value = "备注",required = true)
    private String remark;

}
