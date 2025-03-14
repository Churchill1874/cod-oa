package com.ent.codoa.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ent.codoa.entity.Inventory;
import org.apache.ibatis.annotations.Select;

public interface InventoryMapper extends BaseMapper<Inventory> {

//    @Select("SELECT *\n" +
//            "FROM inventory\n" +
//            "WHERE expiration_date <= DATE_ADD(CURDATE(), INTERVAL 2 MONTH) and status=0")
//    IPage<Inventory> selectExpiring(IPage<Inventory> page);


}
