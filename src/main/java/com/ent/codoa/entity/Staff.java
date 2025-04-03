package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.entity.base.BaseInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@TableName("staff")
public class Staff extends BaseInfo implements Serializable {
    private static final long serialVersionUID = -1438576880084999293L;

    @ApiModelProperty("员工姓名")
    private String name;
    @ApiModelProperty("账号")
    private String account;
    @JsonIgnore
    @ApiModelProperty("密码")
    private String password;
    @JsonIgnore
    @ApiModelProperty("盐值")
    private String salt;
    @ApiModelProperty("年龄")
    private String age;
    @ApiModelProperty("出生日")
    private LocalDate birth;
    @ApiModelProperty("性别")
    private String gender;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("部门")
    private String department;
    @ApiModelProperty("职位")
    private String position;
    @ApiModelProperty("职级")
    private String level;
    @ApiModelProperty("薪资")
    private String salary;
    @ApiModelProperty("入职时间")
    private LocalDate hireDate;
    @ApiModelProperty("工作状态 如在职、离职、休假等")
    private String workStatus;
    @ApiModelProperty("账号状态")
    private UserStatusEnum status;
    @ApiModelProperty("邀请书状态 如offer已发,以拒绝")
    private String offerStatus;
    @ApiModelProperty("编码")
    private String code;
    @ApiModelProperty("合同")
    private String contract;
    @ApiModelProperty("雇佣到期时间")
    private LocalDate employmentExpire;
/*    @ApiModelProperty("每月交税比例")
    private Integer payTaxesRate;*/
    @ApiModelProperty("年假天数")
    private Integer annualLeave;
    @ApiModelProperty("剩余休假 这里面包括了调休增加的")
    private Integer remainingLeave;

    @ApiModelProperty("工作日加班费比例")
    private BigDecimal weekdayOvertimePayRate;
    @ApiModelProperty("周末加班费比例")
    private BigDecimal weekendOvertimePayRate;
    @ApiModelProperty("所属系统用户账号")
    private String systemClientAccount;

}
