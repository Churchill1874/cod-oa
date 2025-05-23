package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.CustomerWorkOrderDialogue;
import com.ent.codoa.pojo.req.IdPageBase;
import com.ent.codoa.pojo.req.PageBase;
import com.ent.codoa.pojo.req.customerworkorderdialogue.CustomerWorkOrderDialoguePage;

public interface CustomerWorkOrderDialogueService extends IService<CustomerWorkOrderDialogue> {

    IPage<CustomerWorkOrderDialogue> queryPage(CustomerWorkOrderDialoguePage dto);

    void systemClientReply(CustomerWorkOrderDialogue dto);

    void customerReply(CustomerWorkOrderDialogue dto);

    IPage<CustomerWorkOrderDialogue> clientPage(CustomerWorkOrderDialoguePage dto);

}
