package com.ent.codoa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ent.codoa.entity.Training;
import com.ent.codoa.pojo.req.training.TrainingAdd;
import com.ent.codoa.pojo.resp.training.TrainingVO;


public interface TrainingService extends IService<Training> {

    TrainingVO trainingVo(String account);

    void add(TrainingAdd dto);

    void delete(Long id);

}
