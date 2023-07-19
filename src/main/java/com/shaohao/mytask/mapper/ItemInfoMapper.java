package com.shaohao.mytask.mapper;

import com.shaohao.mytask.entity.ItemInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 起源岛_item_info Mapper 接口
 * </p>
 *
 * @author shaohao
 * @since 2023-07-03
 */
@Repository
@Mapper
public interface ItemInfoMapper extends BaseMapper<ItemInfo> {

}
