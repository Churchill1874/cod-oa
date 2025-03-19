package com.ent.codoa.pojo.req.staff;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StaffAdd implements Serializable {
    private static final long serialVersionUID = -8206523318503478043L;

    @NotBlank(message = "员工姓名不能为空")
    @Length(max = 30, message = "员工姓名不能超过30位")
    @ApiModelProperty(value = "员工姓名", required = true)
    private String name;

    @NotBlank(message = "年龄不能为空")
    @Length(max = 5, message = "年龄不能超过5位")
    @ApiModelProperty(value = "年龄", required = true)
    private String age;

    @NotNull(message = "出生日期不能为空")
    @ApiModelProperty(value = "出生日", required = true)
    private LocalDate birth;

    @NotBlank(message = "性别不能为空")
    @Length(max = 3, message = "性别不能超过3位")
    @ApiModelProperty(value = "性别", required = true)
    private String gender;

    @NotBlank(message = "手机不能为空")
    @Length(max = 20, message = "手机不能超过20位")
    @ApiModelProperty(value = "手机", required = true)
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Length(max = 50, message = "邮箱不能超过50位")
    @ApiModelProperty(value = "邮箱", required = true)
    private String email;

    @NotBlank(message = "地址不能为空")
    @Length(max = 100, message = "地址不能超过100位")
    @ApiModelProperty(value = "地址", required = true)
    private String address;

    @NotBlank(message = "部门不能为空")
    @Length(max = 20, message = "部门不能超过20位")
    @ApiModelProperty(value = "部门", required = true)
    private String department;

    @NotBlank(message = "职业不能为空")
    @Length(max = 20, message = "职业不能超过20位")
    @ApiModelProperty(value = "职位", required = true)
    private String position;

    @NotBlank(message = "职级不能为空")
    @Length(max = 10, message = "职级不能超过10位")
    @ApiModelProperty(value = "职级", required = true)
    private String level;

    @NotBlank(message = "薪资不能为空")
    @Length(max = 20, message = "薪资不能超过20位")
    @ApiModelProperty(value = "薪资", required = true)
    private String salary;

    @NotNull(message = "入职时间不能为空")
    @ApiModelProperty(value = "入职时间", required = true)
    private LocalDate hireDate;

    @NotBlank(message = "工作状态不能为空")
    @Length(max = 10, message = "工作状态不能超过10位")
    @ApiModelProperty(value = "工作状态 如在职、离职、休假等", required = true)
    private String workStatus;

    @NotBlank(message = "邀请书不能为空")
    @Length(max = 10, message = "邀请书不能超过10位")
    @ApiModelProperty(value = "邀请书状态 如offer已发,以拒绝", required = true)
    private String offerStatus;

    @Length(max = 30, message = "账号不能超过30位")
    @ApiModelProperty(value = "账号")
    private String account;

    @Length(max = 20, message = "密码不能超过20位")
    @ApiModelProperty("密码")
    private String password;

    @Length(max = 20, message = "编码不能超过20位")
    @ApiModelProperty("编码")
    private String code;

    @NotNull(message = "雇佣到期时间不能为空")
    @ApiModelProperty(value = "雇佣到期时间", required = true)
    private LocalDate employmentExpire;

    @NotNull(message = "每月交税比例不能为空")
    @ApiModelProperty(value = "每月交税比例", required = true)
    private Integer payTaxesRate;

    @NotNull(message = "工作日加班费比例不能为空")
    @ApiModelProperty(value = "工作日加班费比例", required = true)
    private Integer weekdayOverTimePayRate;

    @NotNull(message = "周末加班费比例不能为空")
    @ApiModelProperty(value = "周末加班费比例", required = true)
    private Integer weekendOverTimePayRate;

}
