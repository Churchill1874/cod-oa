package com.ent.codoa.pojo.req.salarysettlement;

import com.ent.codoa.pojo.req.PageBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class SalarySettlementPage extends PageBase implements Serializable {
    private static final long serialVersionUID = 7984882319860024332L;

    @ApiModelProperty("日期 年-月 没有日")
    private String date;
    @ApiModelProperty("账号")
    private String account;

}
