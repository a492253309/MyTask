package com.shaohao.mytask.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaohao.mytask.entity.WorldFairy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 起源岛_岛屿 Mapper 接口
 * </p>
 *
 * @author shaohao
 * @since 2023-06-27
 */
@Repository
@Mapper
public interface WorldFairyMapper extends BaseMapper<WorldFairy> {

//    int create(WorldFairy worldFairy);
//
//    int delete(Map<String,Object> paraMap);
//
//    int update(Map<String,Object> paraMap);
//
    WorldFairy query(@Param("address") String address);
}
