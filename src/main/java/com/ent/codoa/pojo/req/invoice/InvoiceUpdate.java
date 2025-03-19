package com.ent.codoa.pojo.req.invoice;

import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class InvoiceUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = -3724380794692554113L;

    @ApiModelProperty(value = "发票图片的Base64数据")
    private String  image;

}
