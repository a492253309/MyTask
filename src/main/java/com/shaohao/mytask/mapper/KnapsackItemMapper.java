package com.shaohao.mytask.mapper;

import com.shaohao.mytask.entity.KnapsackItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 起源岛_背包 Mapper 接口
 * </p>
 *
 * @author shaohao
 * @since 2023-07-02
 */
@Repository
@Mapper
public interface KnapsackItemMapper extends BaseMapper<KnapsackItem> {

}
