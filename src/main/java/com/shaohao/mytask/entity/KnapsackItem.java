package com.shaohao.mytask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 起源岛_背包
 * </p>
 *
 * @author shaohao
 * @since 2023-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_knapsack_item")
public class KnapsackItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty(value = "id")
    private Integer itemId;

//    @ApiModelProperty(value = "名")
    private String itemName;

//    @ApiModelProperty(value = "数量")
    private double icount;

//    @ApiModelProperty(value = "钱包地址")
    private String address;

//    @ApiModelProperty(value = "创建时间")
    private Integer createDate;

//    @ApiModelProperty(value = "创建人")
    private Integer createUser;

//    @ApiModelProperty(value = "更新时间")
    private Integer updateDate;


}
