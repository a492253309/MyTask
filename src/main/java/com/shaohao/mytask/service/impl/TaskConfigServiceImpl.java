package com.shaohao.mytask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shaohao.mytask.entity.TaskConfig;
import com.shaohao.mytask.mapper.TaskConfigMapper;
import com.shaohao.mytask.service.TaskConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 任务配置 服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2023-07-19
 */
@Service
public class TaskConfigServiceImpl extends ServiceImpl<TaskConfigMapper, TaskConfig> implements TaskConfigService {


    @Override
    public String getWallet(String token) {
        QueryWrapper<TaskConfig> query = new QueryWrapper<>();
        query.eq("token",token);
        TaskConfig entity = baseMapper.selectOne(query);
        return entity.getAddress();
    }

    @Override
    public boolean setWallet(String token, String wallet) {
        QueryWrapper<TaskConfig> query = new QueryWrapper<>();
        query.eq("token",token);
        TaskConfig entity = baseMapper.selectOne(query);
        entity.setAddress(wallet);
        UpdateWrapper<TaskConfig> update = new UpdateWrapper<>();
        update.eq("token",token);
        return baseMapper.update(entity,update) >0;
    }

    @Override
    public boolean setRound(String wallet) {
        QueryWrapper<TaskConfig> query = new QueryWrapper<>();
        query.eq("address",wallet);
        TaskConfig entity = baseMapper.selectOne(query);
        entity.setRound(entity.getRound()+1);
        UpdateWrapper<TaskConfig> update = new UpdateWrapper<>();
        update.eq("address",wallet);
        return baseMapper.update(entity,update) >0;
    }
}
