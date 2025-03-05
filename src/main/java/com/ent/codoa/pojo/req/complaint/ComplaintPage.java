package com.ent.codoa.pojo.req.complaint;

import com.ent.codoa.pojo.req.PageBase;
import lombok.Data;

import java.io.Serializable;

@Data
public class ComplaintPage extends PageBase implements Serializable {
    private static final long serialVersionUID = 601926199403305373L;

    private String account;

}
