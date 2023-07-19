package com.shaohao.mytask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shaohao.mytask.entity.WorldFairy;
import com.shaohao.mytask.mapper.WorldFairyMapper;
import com.shaohao.mytask.service.WorldFairyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 起源岛_岛屿 服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2023-06-27
 */
@Service
public class WorldFairyServiceImpl extends ServiceImpl<WorldFairyMapper, WorldFairy> implements WorldFairyService {


//    @Autowired
//    public WorldFairyMapper mapper;
    @Override
    public Boolean insert(WorldFairy worldFairy) {
        int insert = baseMapper.insert(worldFairy);
        return insert > 0;
    }

    @Override
    public Boolean deleteByList(List<Integer> ids) {
        int delete = baseMapper.deleteBatchIds(ids);
        return delete > 0;
    }

    @Override
    public Boolean update(UpdateWrapper updateWrapper, WorldFairy worldFairy) {
        int update = baseMapper.update(worldFairy, updateWrapper);
        return update > 0;
    }

    @Override
    public List<WorldFairy> queryList(QueryWrapper<WorldFairy> queryWrapper) {
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public WorldFairy queryOne(String address) {
        return baseMapper.query(address);
    }
}
