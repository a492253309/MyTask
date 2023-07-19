package com.shaohao.mytask.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shaohao.mytask.entity.WorldFairy;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 起源岛_岛屿 服务类
 * </p>
 *
 * @author shaohao
 * @since 2023-06-27
 */
public interface WorldFairyService extends IService<WorldFairy> {
    /**
     * 新增起源岛
     *
     * @param worldFairy 起源岛
     * @return Boolean
     */
    Boolean insert(WorldFairy worldFairy);

    /**
     * 删除起源岛
     *
     * @param ids
     * @return Boolean
     */
    Boolean deleteByList(List<Integer> ids);

    /**
     * 更新起源岛
     *
     * @param updateWrapper
     * @param worldFairy
     * @return
     */
    Boolean update(UpdateWrapper updateWrapper, WorldFairy worldFairy);

    /**
     * 查询起源岛
     *
     * @param queryWrapper
     * @return List<WorldFairy>
     */
    List<WorldFairy> queryList(QueryWrapper<WorldFairy> queryWrapper);

    /**
     * 查询起源岛
     *
     * @param address
     * @return WorldFairy
     */
//    WorldFairy queryOne(QueryWrapper<WorldFairy> queryWrapper);
    WorldFairy queryOne(String address);

}
