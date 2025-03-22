package com.ent.codoa.controller.client;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.pojo.resp.training.TrainingVO;
import com.ent.codoa.service.TrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Api(tags = "培训")
@RequestMapping("/client/training")
public class TrainingApi {

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/trainingList")
    @ApiOperation(value = "培训信息集合查询", notes = "培训信息集合 已经根据类型分组")
    public R<TrainingVO> trainingList() {
        TrainingVO trainingVO = trainingService.trainingVo(TokenTools.getSystemClientAccount());
        return R.ok(trainingVO);
    }

}
