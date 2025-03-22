package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.pojo.req.IdBase;
import com.ent.codoa.pojo.req.training.TrainingAdd;
import com.ent.codoa.pojo.resp.training.TrainingVO;
import com.ent.codoa.service.TrainingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@Api(tags = "培训")
@RequestMapping("/admin/training")
public class TrainingController {

    @Autowired
    private TrainingService trainingService;

    @PostMapping("/trainingList")
    @ApiOperation(value = "培训信息集合查询", notes = "培训信息集合 已经根据类型分组")
    public R<TrainingVO> trainingList() {
        TrainingVO trainingVO = trainingService.trainingVo(TokenTools.getAccount());
        return R.ok(trainingVO);
    }


    @PostMapping("/add")
    @ApiOperation(value = "新增培训信息", notes = "新增培训信息")
    public R add(@RequestBody @Valid TrainingAdd req) {
        trainingService.add(req);
        return R.ok(null);
    }


    @PostMapping("/delete")
    @ApiOperation(value = "根据id删除培训信息", notes = "根据id删除培训信息")
    public R delete(@RequestBody @Valid IdBase req) {
        trainingService.delete(req.getId());
        return R.ok(null);
    }




}
