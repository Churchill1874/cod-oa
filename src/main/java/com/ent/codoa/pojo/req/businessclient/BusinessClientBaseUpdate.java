package com.ent.codoa.pojo.req.businessclient;

import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class BusinessClientBaseUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -8919634860567720896L;

    @NotBlank(message = "名称不能为空")
    @Length(max = 30, message = "请输入30位以内的名称")
    @ApiModelProperty("名称")
    private String name;

}
