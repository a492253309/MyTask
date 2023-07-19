package com.shaohao.mytask.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 起源岛_岛屿
 * </p>
 *
 * @author shaohao
 * @since 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_world_fairy")
public class WorldFairy implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 钱包地址
     */
    @TableField("address")
    private String address;

    /**
     * token
     */
    private String token;

    /**
     * 岛屿编号
     */
    @TableField("uid")
    private Integer uid;

    /**
     * 种植等级
     */
    @TableField("farm_level")
    private Integer farmLevel;

    /**
     * 采集等级
     */
    @TableField("collect_level")
    private Integer collectLevel;

    /**
     * 畜牧等级
     */
    @TableField("ranch_level")
    private Integer ranchLevel;

    /**
     * 土地数
     */
    @TableField("land_num")
    private Integer landNum;

    /**
     * 创建时间
     */
    private Integer createDate;

    /**
     * 创建人
     */
    private Integer createUser;

    private Integer updateDate;


}
