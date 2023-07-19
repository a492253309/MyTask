package com.shaohao.mytask.mapper;

import com.shaohao.mytask.entity.TaskConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 任务配置 Mapper 接口
 * </p>
 *
 * @author shaohao
 * @since 2023-07-19
 */
@Repository
@Mapper
public interface TaskConfigMapper extends BaseMapper<TaskConfig> {

}
