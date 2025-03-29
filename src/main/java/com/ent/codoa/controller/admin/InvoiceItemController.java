package com.ent.codoa.controller.admin;

import com.baomidou.mybatisplus.extension.api.R;
import com.ent.codoa.common.annotation.LoginCheck;
import com.ent.codoa.pojo.req.invoiceitem.InvoiceItemAmount;
import com.ent.codoa.pojo.req.invoiceitem.InvoiceItemTax;
import com.ent.codoa.service.InvoiceItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@RestController
@Api(tags = "支付管理-申报辅助")
@RequestMapping("/admin/invoiceitem")
public class InvoiceItemController {
    @Autowired
    private InvoiceItemService invoiceItemService;

    @LoginCheck
    @PostMapping("/calculateSubAmount")
    @ApiOperation(value="根据商品数量和商品单价计算该明细商品总金额",notes = "根据商品数量和商品单价计算该明细商品总金额")
    public R<BigDecimal> calculateSubAmount(@RequestBody @Valid InvoiceItemAmount req){
        BigDecimal subAmount = invoiceItemService.calculateSubAmount(req);
        return R.ok(subAmount);

    }

    @LoginCheck
    @PostMapping("/calculateSubTax")
    @ApiOperation(value="根据商品总金额和商品税率计算该明细消费税",notes = "根据商品总金额和商品税率计算该明细消费税")
    public R<BigDecimal> calculateSubTax(@RequestBody @Valid InvoiceItemTax req){
        BigDecimal subTax = invoiceItemService.calculateSubTax(req);
        return R.ok(subTax);

    }
}
