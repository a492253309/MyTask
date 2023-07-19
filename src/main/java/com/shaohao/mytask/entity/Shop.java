package com.shaohao.mytask.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


/**
 * <p>
 * 起源岛_商店
 * </p>
 *
 * @author shaohao
 * @since 2023-06-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名
     */
    @TableField("item_name")
    private String itemName;

    /**
     * 商品id
     */
    @TableField("item_id")
    private Integer itemId;

    /**
     * 价格
     */
    @TableField("price")
    private Double price;

    /**
     * 当前库存
     */
    @TableField("current_num")
    private Integer currentNum;

    /**
     * 库存上限
     */
    @TableField("max_num")
    private Integer maxNum;

    /**
     * 钱包地址
     */
    @TableField("address")
    private String address;

    /**
     * 创建时间
     */
    @TableField("create_date")
    private Integer createDate;

    /**
     * 创建人
     */
    @TableField("create_user")
    private Integer createUser;

    /**
     * 更新时间
     */
    @TableField("update_date")
    private Integer updateDate;
}
