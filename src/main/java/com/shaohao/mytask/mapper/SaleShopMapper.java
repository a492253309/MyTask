package com.shaohao.mytask.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaohao.mytask.entity.SaleShop;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SaleShopMapper extends BaseMapper<SaleShop> {

}
