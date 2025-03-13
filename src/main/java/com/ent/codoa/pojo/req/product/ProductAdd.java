package com.ent.codoa.pojo.req.product;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ProductAdd  implements Serializable {
    private static final long serialVersionUID = -6632681633491282644L;
    @NotBlank(message = "商品名称不能为空")
    @Length(max = 30,message = "商品名称长度不能大于30")
    @ApiModelProperty(value = "商品名称",required = true)
    private String name;

    @Length(max = 100,message = "商品描述长度不能大于30")
    @ApiModelProperty(value = "商品描述")
    private String description;

    @NotBlank(message = "商品类别不能为空")
    @Length(max = 20,message = "商品类别长度不能大于20")
    @ApiModelProperty(value = "商品类别",required = true)
    private String category;

    @NotBlank(message = "商品单位不能为空")
    @Length(max = 10,message = "商品类别长度不能大于10")
    @ApiModelProperty(value = "商品单位",required = true)
    private String unit;

    @Positive(message = "仓库id数值错误")
    @NotNull(message = "仓库id不能为空")
    @ApiModelProperty(value = "仓库id",required = true)
    private Long warehouseId;


    @NotNull(message = "预警数量不能为空")
    @ApiModelProperty(value = "预警数量",required = true)
    private Integer warningQuantity;

}
