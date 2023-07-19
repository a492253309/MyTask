package com.shaohao.mytask.service;

import com.shaohao.mytask.entity.TaskConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 任务配置 服务类
 * </p>
 *
 * @author shaohao
 * @since 2023-07-19
 */
public interface TaskConfigService extends IService<TaskConfig> {

    String getWallet(String token);

    boolean setWallet(String token,String wallet);

    boolean setRound(String wallet);
}
