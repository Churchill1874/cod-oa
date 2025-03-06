package com.ent.codoa.pojo.req.systemclient;

import com.ent.codoa.common.constant.enums.SystemClientStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class SystemClientBaseUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 3130509870368265812L;

    @Length(max = 30, message = "请输入30位以内的名称")
    @ApiModelProperty(value = "名称",required = true)
    private String name;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态",required = true)
    private SystemClientStatusEnum status;

    @Length(max = 255, message = "请输入255位以内的描述")
    @ApiModelProperty("描述")
    private String introduce;

    @NotNull(message = "到期时间不能为空")
    @ApiModelProperty("到期时间")
    private LocalDate expiredTime;

    @NotNull(message = "请设置客户管理权限")
    @ApiModelProperty(value = "客户管理权限",required = true)
    private Boolean customerMenu;

    @NotNull(message = "请设置库存管理权限")
    @ApiModelProperty(value = "库存管理权限",required = true)
    private Boolean inventoryMenu;

    @NotNull(message = "请设置人事管理权限")
    @ApiModelProperty(value = "人事管理权限",required = true)
    private Boolean hrMenu;

    @NotNull(message = "请设置支付管理权限")
    @ApiModelProperty(value = "支付管理权限",required = true)
    private Boolean paymentMenu;

    @NotNull(message = "请设置平台管理菜单")
    @ApiModelProperty(value = "平台管理菜单",required = true)
    private Boolean platformMenu;


}
