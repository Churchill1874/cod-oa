package com.ent.codoa.pojo.resp.systemclient;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class CaptchaCode implements Serializable {

    private static final long serialVersionUID = -7505830911231479875L;
    @ApiModelProperty("验证码图片")
    private String captchaImage;

}
