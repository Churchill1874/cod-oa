package com.ent.codoa.pojo.req.warehouse;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class WarehouseAdd implements Serializable {
    private static final long serialVersionUID = -7862494432054995762L;
    @NotBlank(message = "仓库名称不能为空")
    @Length(max=30,message = "仓库名称不能大于30个字")
    @ApiModelProperty(value = "仓库名称 30位长度",required = true)
    private String name;

    @NotBlank(message = "仓库地址不能为空")
    @Length(max = 50,message = "仓库地址不能大于50个字")
    @ApiModelProperty(value = "仓库地址 50位长度",required = true)
    private String address;
}
