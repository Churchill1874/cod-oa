package com.ent.codoa.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ent.codoa.entity.Product;
import com.ent.codoa.pojo.resp.product.ProductQantityVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProductMapper extends BaseMapper<Product> {

    @Select({"SELECT \n" +
            "    p.id, \n" +
            "    p.name, \n" +
            "    p.warning_quantity, \n" +
            "   p.category,\n" +
            "   p.unit,\n" +
            "   p.warehouse_id,\n" +
            "    COALESCE(SUM(i.quantity), 0) AS totalQuantity\n" +
            "FROM \n" +
            "    product p\n" +
            "LEFT JOIN \n" +
            "    inventory i ON p.id = i.product_id AND i.status = 0\n" +
            "GROUP BY \n" +
            "    p.id, p.name, p.warning_quantity\n" +
            "HAVING \n" +
            "    p.warning_quantity >= COALESCE(SUM(i.quantity), 0) AND p.warehouse_id = #{warehouseId}"})
    IPage<ProductQantityVO> findProductsWithStockWarning(IPage<ProductQantityVO> iPage,
                                                         @Param("warehouseId") Long warehouseId);
}
