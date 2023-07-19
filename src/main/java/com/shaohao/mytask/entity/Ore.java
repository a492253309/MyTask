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
 * 起源岛_矿物
 * </p>
 *
 * @author shaohao
 * @since 2023-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_ore")
public class Ore implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty(value = "矿物id")
    private Integer oreId;

//    @ApiModelProperty(value = "矿物名")
    private String oreName;

//    @ApiModelProperty(value = "成熟时间")
    private Integer unlockAt;

//    @ApiModelProperty(value = "钱包地址")
    private String address;

//    @ApiModelProperty(value = "创建时间")
    private Integer createDate;

//    @ApiModelProperty(value = "创建人")
    private Integer createUser;

//    @ApiModelProperty(value = "更新时间")
    private Integer updateDate;

    private Integer type;


}
