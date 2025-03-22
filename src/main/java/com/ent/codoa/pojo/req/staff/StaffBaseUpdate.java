package com.ent.codoa.pojo.req.staff;

import com.ent.codoa.common.constant.enums.UserStatusEnum;
import com.ent.codoa.pojo.req.IdBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class StaffBaseUpdate extends IdBase implements Serializable {
    private static final long serialVersionUID = 9042987427204035489L;

    @NotBlank(message = "员工姓名不能为空")
    @Length(max = 30, message = "员工姓名不能超过30位")
    @ApiModelProperty("员工姓名")
    private String name;

    @NotBlank(message = "年龄不能为空")
    @Length(max = 5, message = "年龄不能超过5位")
    @ApiModelProperty("年龄")
    private String age;

    @NotNull(message = "出生日期不能为空")
    @ApiModelProperty("出生日")
    private LocalDate birth;

    @NotBlank(message = "性别不能为空")
    @Length(max = 3, message = "性别不能超过3位")
    @ApiModelProperty("性别")
    private String gender;

    @NotBlank(message = "手机不能为空")
    @Length(max = 20, message = "手机不能超过20位")
    @ApiModelProperty("手机")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    @Length(max = 50, message = "邮箱不能超过50位")
    @ApiModelProperty("邮箱")
    private String email;

    @NotBlank(message = "地址不能为空")
    @Length(max = 100, message = "地址不能超过100位")
    @ApiModelProperty("地址")
    private String address;

    @NotBlank(message = "部门不能为空")
    @Length(max = 20, message = "部门不能超过20位")
    @ApiModelProperty("部门")
    private String department;

    @NotBlank(message = "职业不能为空")
    @Length(max = 20, message = "职业不能超过20位")
    @ApiModelProperty("职位")
    private String position;

    @NotBlank(message = "职级不能为空")
    @Length(max = 10, message = "职级不能超过10位")
    @ApiModelProperty("职级")
    private String level;

    @NotBlank(message = "薪资不能为空")
    @Length(max = 20, message = "薪资不能超过20位")
    @ApiModelProperty("薪资")
    private String salary;

    @NotNull(message = "入职时间不能为空")
    @ApiModelProperty("入职时间")
    private LocalDate hireDate;

    @NotBlank(message = "工作状态不能为空")
    @Length(max = 10, message = "工作状态不能超过10位")
    @ApiModelProperty("工作状态 如在职、离职、休假等")
    private String workStatus;

    @NotBlank(message = "邀请书不能为空")
    @Length(max = 10, message = "邀请书不能超过10位")
    @ApiModelProperty("邀请书状态 如offer已发,以拒绝")
    private String offerStatus;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态", required = true)
    private UserStatusEnum status;

    @NotNull(message = "雇佣到期时间不能为空")
    @ApiModelProperty(value = "雇佣到期时间", required = true)
    private LocalDate employmentExpire;

    @NotNull(message = "每月交税比例不能为空")
    @ApiModelProperty(value = "每月交税比例", required = true)
    private Integer payTaxesRate;

    @NotNull(message = "工作日加班费比例不能为空")
    @ApiModelProperty(value = "工作日加班费比例", required = true)
    private Integer weekdayOvertimePayRate;

    @NotNull(message = "周末加班费比例不能为空")
    @ApiModelProperty(value = "周末加班费比例", required = true)
    private Integer weekendOvertimePayRate;

    @NotNull(message = "年假天数不能为空")
    @ApiModelProperty(value = "年假天数",required = true)
    private Integer annualLeave;

    @NotNull(message = "剩余休假不能为空")
    @ApiModelProperty(value = "剩余休假 这里面包括了调休增加的",required = true)
    private Integer remainingLeave;

}
