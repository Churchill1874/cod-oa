package com.ent.codoa.common.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *计算器工具
 */

public class BigDecimalTools {

    /**
     * 返回字符串两位小数的除法运算结果
     * 并且不四舍五入直接截取
     * @param dividend
     * @param divisor
     * @return
     */
    public static String divideK2AndDownStr(BigDecimal dividend, BigDecimal divisor) {
        if (BigDecimal.ZERO.compareTo(divisor) == 0){
            return "0.00";
        }
        return dividend.divide(divisor, 2, RoundingMode.DOWN).toPlainString();
    }


    /**
     * 返回两位小数的除法运算结果
     * 并且不四舍五入直接截取
     * @param dividend
     * @param divisor
     * @return
     */
    public static BigDecimal divideK2AndDown(BigDecimal dividend, BigDecimal divisor) {
        if (BigDecimal.ZERO.compareTo(divisor) == 0){
            return new BigDecimal("0.00").setScale(2, RoundingMode.DOWN);
        }
        return dividend.divide(divisor, 2, RoundingMode.DOWN);
    }

}
