package com.ent.codoa.service.serviceimpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ent.codoa.common.tools.LogTools;
import com.ent.codoa.common.tools.TokenTools;
import com.ent.codoa.entity.Training;
import com.ent.codoa.mapper.TrainingMapper;
import com.ent.codoa.pojo.req.training.TrainingAdd;
import com.ent.codoa.pojo.resp.training.TrainingVO;
import com.ent.codoa.service.TrainingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl extends ServiceImpl<TrainingMapper, Training> implements TrainingService {

    @Override
    public TrainingVO trainingVo(String systemClientAccount) {
        QueryWrapper<Training> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
            .eq(Training::getSystemClientAccount, systemClientAccount)
            .orderByDesc(Training::getCreateTime);
        List<Training> list = list(queryWrapper);

        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        Map<String, List<Training>> map = list.stream().collect(Collectors.groupingBy(Training::getType));

        TrainingVO trainingVO = new TrainingVO();

        map.forEach((key, trainings) -> {
            if ("规章制度".equals(key) || "規則".equals(key)) {
                trainingVO.setRulesList(trainings);
            }
            if ("业务文档".equals(key) || "業務資料".equals(key)) {
                trainingVO.setBusinessList(trainings);
            }
            if ("技术相关".equals(key) || "技術関連".equals(key)) {
                trainingVO.setTechnologyList(trainings);
            }
            if ("学习视频".equals(key) || "学習動画".equals(key)) {
                trainingVO.setVideoList(trainings);
            }
        });

        return trainingVO;
    }

    @Override
    public void add(TrainingAdd dto) {
        Training training = BeanUtil.toBean(dto, Training.class);
        if("jp".equals(TokenTools.getLoginLang()) && "规章制度".equals(dto.getType())){
            training.setType("規則");
        }
        if("jp".equals(TokenTools.getLoginLang()) && "业务文档".equals(dto.getType())){
            training.setType("業務資料");
        }
        if("jp".equals(TokenTools.getLoginLang()) && "技术相关".equals(dto.getType())){
            training.setType("技術関連");
        }
        if("jp".equals(TokenTools.getLoginLang()) && "学习视频".equals(dto.getType())){
            training.setType("学習動画");
        }
        training.setSystemClientAccount(TokenTools.getAccount());
        training.setCreateName(TokenTools.getName());
        training.setCreateTime(LocalDateTime.now());
        save(training);

        LogTools.addLog("培训", "新增一条培训记录:" + JSONUtil.toJsonStr(training), TokenTools.getLoginToken(true));
    }

    @Override
    public void delete(Long id) {
        removeById(id);
        LogTools.addLog("培训", "删除一条培训信息:" + id, TokenTools.getLoginToken(true));
    }

}
