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
 * 起源岛_item_info
 * </p>
 *
 * @author shaohao
 * @since 2023-07-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_item_info")
public class ItemInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty(value = "item_id")
    private Integer itemId;

//    @ApiModelProperty(value = "名")
    private String itemName;

//    @ApiModelProperty(value = "价格")
    private Double price;

//    @ApiModelProperty(value = "时长")
    private Integer hours;

//    @ApiModelProperty(value = "1:种子 2:成熟农作物 3：其他")
    private Integer type;

//    @ApiModelProperty(value = "创建时间")
    private Integer createDate;

//    @ApiModelProperty(value = "创建人")
    private Integer createUser;

//    @ApiModelProperty(value = "更新时间")
    private Integer updateDate;


}
