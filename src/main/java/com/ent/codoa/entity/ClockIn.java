package com.ent.codoa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("clock_in")
public class ClockIn implements Serializable {
    private static final long serialVersionUID = -8888908587384186333L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("上班打卡时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @ApiModelProperty("下班打卡时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @ApiModelProperty("系统管理员账号")
    private String systemClientAccount;
    @ApiModelProperty("状态 正常 , 迟到打卡 , 早退打卡 , 迟到打卡/早退打卡, 缺勤 ")
    private String status;

}
