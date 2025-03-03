package com.ent.codoa.pojo.req.warehouse;


import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WarehouseBaseUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -6012103713036022607L;

    @NotBlank(message = "仓库名称不能为空")
    @Length(max = 30,message = "仓库名称不能超过30个字")
    @ApiModelProperty("仓库名称 30位长度")
    private String name;

    @NotBlank(message = "仓库地址不能为空")
    @Length(max = 50,message = "地址不能超过50个字")
    @ApiModelProperty("仓库地址 50位长度")
    private String address;
}
