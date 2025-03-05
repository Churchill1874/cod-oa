package com.ent.codoa.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.PotentialClient;
import com.ent.codoa.pojo.req.potentialclent.PotentialClientPage;

public interface PotentialClientService extends IService<PotentialClient> {

    IPage<PotentialClient> queryPage(PotentialClientPage dto);

    void add(PotentialClient dto);

    void delete(Long id);

    void updateInfo(PotentialClient dto);

}
