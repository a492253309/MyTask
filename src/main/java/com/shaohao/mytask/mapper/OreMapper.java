package com.shaohao.mytask.mapper;

import com.shaohao.mytask.entity.Ore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 起源岛_矿物 Mapper 接口
 * </p>
 *
 * @author shaohao
 * @since 2023-07-02
 */
@Repository
@Mapper
public interface OreMapper extends BaseMapper<Ore> {

}
