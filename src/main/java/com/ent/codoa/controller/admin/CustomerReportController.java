package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.CustomerAuthCheck;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.common.tools.BigDecimalTools;
import com.ent.codoa.common.tools.TimeTools;
import com.ent.codoa.entity.Complaint;
import com.ent.codoa.entity.CustomerOrder;
import com.ent.codoa.entity.SystemClient;
import com.ent.codoa.pojo.resp.customerreport.CustomerReportVO;
import com.ent.codoa.pojo.resp.customerreport.LossReasonData;
import com.ent.codoa.pojo.resp.customerreport.LossWarningVO;
import com.ent.codoa.pojo.resp.customerreport.MonthStatisticsVO;
import com.ent.codoa.service.ComplaintService;
import com.ent.codoa.service.CustomerOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private ComplaintService complaintService;
    @Autowired
    private CustomerOrderService customerOrderService;

    @CustomerAuthCheck
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
            if (customerOrder.getAmount() == null) {
                continue;
            }
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

    @CustomerAuthCheck
    @PostMapping("/profitStatistics")
    @ApiOperation(value = "利润率统计数据", notes = "利润率统计数据")
    public R<CustomerReportVO> profitStatistics() {
        BigDecimal thisWeekSales = BigDecimal.ZERO;
        BigDecimal lastWeekSales = BigDecimal.ZERO;
        BigDecimal thisYearSales = BigDecimal.ZERO;
        BigDecimal lastYearSales = BigDecimal.ZERO;

        BigDecimal thisWeekProfit = BigDecimal.ZERO;
        BigDecimal lastWeekProfit = BigDecimal.ZERO;
        BigDecimal thisYearProfit = BigDecimal.ZERO;
        BigDecimal lastYearProfit = BigDecimal.ZERO;

        List<CustomerOrder> thisYearList = new ArrayList<>();
        List<CustomerOrder> list = customerOrderService.withinTwoYears();

        //获取今年的时间用于对比
        LocalDateTime thisYearTime = LocalDateTime.now();
        //获取今年的日期值
        int thisYearInt = thisYearTime.getYear();
        //获取去年的日起值
        int lastYearInt = thisYearInt - 1;

        for (CustomerOrder customerOrder : list) {
            if (customerOrder.getAmount() == null || customerOrder.getProfit() == null) {
                continue;
            }

            //如果是本周的数据
            if (TimeTools.isThisWeekBetween(customerOrder.getCreateTime())) {
                //累计本周数据
                thisWeekSales = thisWeekSales.add(customerOrder.getAmount());
                thisWeekProfit = thisWeekProfit.add(customerOrder.getProfit());
            }
            //如果是上周的数据
            if (TimeTools.isLastWeekBetween(customerOrder.getCreateTime())) {
                //累计上周数据
                lastWeekSales = lastWeekSales.add(customerOrder.getAmount());
                lastWeekProfit = lastWeekProfit.add(customerOrder.getProfit());
            }
            //如果是今年的数据
            if (customerOrder.getCreateTime().getYear() == thisYearInt) {
                //累计今年数据
                thisYearSales = thisYearSales.add(customerOrder.getAmount());
                thisYearProfit = thisYearProfit.add(customerOrder.getProfit());
                //并且放入今年数据统计集合 后面分组统计每月用
                thisYearList.add(customerOrder);
            }
            //如果是去年的数据
            if (customerOrder.getCreateTime().getYear() == lastYearInt) {
                //累计去年数据
                lastYearSales = lastYearSales.add(customerOrder.getAmount());
                lastYearProfit = lastYearProfit.add(customerOrder.getProfit());
            }
        }

        //每个统计的数据
        List<MonthStatisticsVO> monthStatisticsVOList = new ArrayList<>();

        //对今年数据 进行每个月的分组
        Map<Integer, List<CustomerOrder>> thisYearMap = thisYearList.stream().collect(Collectors.groupingBy(customerOrder -> customerOrder.getCreateTime().getMonthValue()));
        //遍历 并且算出每组的每月统计
        for (Integer key : thisYearMap.keySet()) {
            BigDecimal totalSales = BigDecimal.ZERO;
            BigDecimal totalProfit = BigDecimal.ZERO;

            for (CustomerOrder customerOrder : thisYearMap.get(key)) {
                if (customerOrder.getAmount() != null) {
                    totalSales = totalSales.add(customerOrder.getAmount());
                }
                if (customerOrder.getProfit() != null){
                    totalProfit = totalProfit.add(customerOrder.getProfit());
                }
            }

            MonthStatisticsVO monthStatisticsVO = new MonthStatisticsVO();
            monthStatisticsVO.setDate(key);
            //小数比例
            BigDecimal decimalRate = BigDecimalTools.divideK2AndDown(totalProfit, totalSales);
            monthStatisticsVO.setValue(new BigDecimal("100").multiply(decimalRate).toPlainString());
            monthStatisticsVOList.add(monthStatisticsVO);
        }

        //返回前端的类
        CustomerReportVO customerReportVO = new CustomerReportVO();
        customerReportVO.setThisYearMonthList(monthStatisticsVOList);

        BigDecimal thisWeek = BigDecimalTools.divideK2AndDown(thisWeekProfit, thisWeekSales);
        customerReportVO.setThisWeek(thisWeek.multiply(new BigDecimal("100")).toPlainString());

        BigDecimal lastWeek = BigDecimalTools.divideK2AndDown(lastWeekProfit, lastWeekSales);
        customerReportVO.setLastWeek(lastWeek.multiply(new BigDecimal("100")).toPlainString());

        BigDecimal thisYear = BigDecimalTools.divideK2AndDown(thisYearProfit, thisYearSales);
        customerReportVO.setThisYear(thisYear.multiply(new BigDecimal("100")).toPlainString());

        BigDecimal lastYear = BigDecimalTools.divideK2AndDown(lastYearProfit, lastYearSales);
        customerReportVO.setLastYear(lastYear.multiply(new BigDecimal("100")).toPlainString());

        return R.ok(customerReportVO);
    }

    @CustomerAuthCheck
    @PostMapping("/lossWarning")
    @ApiOperation(value = "客户流失预警", notes = "客户流失预警")
    public R<LossWarningVO> lossWarning() {
        //统计出来 两个业务 上个月和上上个月的比例变化并返回
        LossWarningVO lossWarningVO = new LossWarningVO();
        //当前时间
        LocalDate currentDate = LocalDate.now();
        //上个月
        LocalDate lastMonthDate = currentDate.minusMonths(1);
        String lastMonth = lastMonthDate.getYear() + "" + lastMonthDate.getMonthValue();
        //上上个月
        LocalDate twoMonthAgoDate = currentDate.minusMonths(2);
        String twoMonthAgo = twoMonthAgoDate.getYear() + "" + twoMonthAgoDate.getMonthValue();

        //近三个月投诉记录数量
        List<Complaint> complaintList = complaintService.withinThreeMonth();
        //投诉记录分析
        if (CollectionUtils.isNotEmpty(complaintList)) {
            //上个月投诉记录数量
            long lastMonthCount = complaintList.stream()
                .filter(dto -> (dto.getCreateTime().getYear() + "" + dto.getCreateTime().getMonthValue()).equals(lastMonth)).count();
            //上上个月投诉记录数量
            long twoMonthAgoCount = complaintList.stream()
                .filter(dto -> dto.getCreateTime().toLocalDate().toString().equals(twoMonthAgo)).count();
            //如果上个月比上上个月增加了投诉量
            if (lastMonthCount > twoMonthAgoCount) {
                //增加了的数量
                BigDecimal increasedCount = new BigDecimal(lastMonthCount - twoMonthAgoCount);
                BigDecimal rate = BigDecimalTools.divideK2AndDown(increasedCount, new BigDecimal(twoMonthAgoCount)).multiply(new BigDecimal(100));
                //判断上个月投诉是否比上上个月多了百分之30
                if (rate.intValue() > 30) {
                    //投诉数据对比
                    LossReasonData complaintData = new LossReasonData();
                    complaintData.setLastMonthDate(lastMonth);
                    complaintData.setLastMonthCount(lastMonthCount);
                    complaintData.setTwoMonthAgoDate(twoMonthAgo);
                    complaintData.setTwoMonthAgoCount(twoMonthAgoCount);
                    //上个月比上上个月比例
                    complaintData.setRate(rate.intValue());
                    lossWarningVO.setComplaintComparison(complaintData);
                }
            }
        }

        //近三个月交易订单
        List<CustomerOrder> customerOrderList = customerOrderService.withinThreeMonth();
        //交易量分析
        if (CollectionUtils.isNotEmpty(customerOrderList)) {
            //上个月交易数量
            long lastMonthCount = customerOrderList.stream()
                .filter(dto -> (dto.getCreateTime().getYear() + "" + dto.getCreateTime().getMonthValue()).equals(lastMonth)).count();
            //上上个月交易数量
            long twoMonthAgoCount = customerOrderList.stream()
                .filter(dto -> (dto.getCreateTime().getYear() + "" + dto.getCreateTime().getMonthValue()).equals(twoMonthAgo)).count();

            //如果上个月比上上个月减少了交易量
            if (lastMonthCount < twoMonthAgoCount){
                BigDecimal decreased = new BigDecimal(lastMonthCount - twoMonthAgoCount);
                BigDecimal rate = BigDecimalTools.divideK2AndDown(decreased, new BigDecimal(twoMonthAgoCount)).multiply(new BigDecimal(100));
                //判断上个月交易数量是否比上上个月减少了百分之30以上
                if (rate.intValueExact() <= -30) {
                    //投诉数据对比
                    LossReasonData complaintData = new LossReasonData();
                    complaintData.setLastMonthDate(lastMonth);
                    complaintData.setLastMonthCount(lastMonthCount);
                    complaintData.setTwoMonthAgoDate(twoMonthAgo);
                    complaintData.setTwoMonthAgoCount(twoMonthAgoCount);
                    //上个月比上上个月比例
                    complaintData.setRate(rate.intValueExact());
                    lossWarningVO.setComplaintComparison(complaintData);
                }
            }
        }


        return R.ok(lossWarningVO);
    }

}
