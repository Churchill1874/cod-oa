package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.tools.TimeTools;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.pojo.resp.customerreport.CustomerReportVO;
import com.ent.codoa.pojo.resp.customerreport.MonthStatisticsVO;
import com.ent.codoa.service.CustomerOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "客户管理-分析与报表")
@RequestMapping("/admin/customerReport")
public class CustomerReportController {

    @Autowired
    private CustomerOrderService customerOrderService;

    @PostMapping("/salesStatistics")
    @ApiOperation(value = "销售额统计数据", notes = "销售额统计数据")
    public R<CustomerReportVO> salesStatistics() {
        BigDecimal thisWeek = BigDecimal.ZERO;
        BigDecimal lastWeek = BigDecimal.ZERO;
        BigDecimal thisYear = BigDecimal.ZERO;
        BigDecimal lastYear = BigDecimal.ZERO;
        List<CustomerOrder> thisYearList = new ArrayList<>();
        List<CustomerOrder> list = customerOrderService.withinTwoYears();


        //获取今年的时间用于对比
        LocalDateTime thisYearTime = LocalDateTime.now();
        //获取今年的日期值
        int thisYearInt = thisYearTime.getYear();
        //获取去年的日起值
        int lastYearInt = thisYearInt - 1;

        for (CustomerOrder customerOrder : list) {
            //如果是本周的数据
            if (TimeTools.isThisWeekBetween(customerOrder.getCreateTime())) {
                //累计本周数据
                thisWeek = thisWeek.add(customerOrder.getAmount());
            }
            //如果是上周的数据
            if (TimeTools.isLastWeekBetween(customerOrder.getCreateTime())) {
                //累计上周数据
                lastWeek = lastWeek.add(customerOrder.getAmount());
            }
            //如果是今年的数据
            if (customerOrder.getCreateTime().getYear() == thisYearInt) {
                //累计今年数据
                thisYear = thisYear.add(customerOrder.getAmount());
                //并且放入今年数据统计集合 后面分组统计每月用
                thisYearList.add(customerOrder);
            }
            //如果是去年的数据
            if (customerOrder.getCreateTime().getYear() == lastYearInt) {
                //累计去年数据
                lastYear = lastYear.add(customerOrder.getAmount());
            }
        }


        //每个统计的数据
        List<MonthStatisticsVO> monthStatisticsVOList = new ArrayList<>();

        //对今年数据 进行每个月的分组
        Map<Integer, List<CustomerOrder>> thisYearMap = thisYearList.stream().collect(Collectors.groupingBy(customerOrder -> customerOrder.getCreateTime().getMonthValue()));
        //遍历 并且算出每组的每月统计
        for (Integer key : thisYearMap.keySet()) {
            BigDecimal total = thisYearMap.get(key).stream().map(CustomerOrder::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            MonthStatisticsVO monthStatisticsVO = new MonthStatisticsVO();
            monthStatisticsVO.setDate(key);
            monthStatisticsVO.setValue(total.toPlainString());
            monthStatisticsVOList.add(monthStatisticsVO);
        }


        //返回前端的类
        CustomerReportVO customerReportVO = new CustomerReportVO();
        customerReportVO.setThisWeek(thisWeek.toPlainString());
        customerReportVO.setLastWeek(lastWeek.toPlainString());
        customerReportVO.setThisYear(thisYear.toPlainString());
        customerReportVO.setLastYear(lastYear.toPlainString());
        customerReportVO.setThisYearMonthList(monthStatisticsVOList);
        return R.ok(customerReportVO);
    }

}
