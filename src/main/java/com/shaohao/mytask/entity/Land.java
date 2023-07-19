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
 * 起源岛_土地
 * </p>
 *
 * @author shaohao
 * @since 2023-07-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("wf_land")
public class Land implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

//    @ApiModelProperty(value = "土地id")
    private Integer landId;

//    @ApiModelProperty(value = "农作物id")
    private Integer seedId;

//    @ApiModelProperty(value = "农作物名")
    private String seedName;

//    @ApiModelProperty(value = "成熟时间")
    private Integer harvestAt;

//    @ApiModelProperty(value = "钱包地址")
    private String address;

//    @ApiModelProperty(value = "创建时间")
    private Integer createDate;

//    @ApiModelProperty(value = "创建人")
    private Integer createUser;

//    @ApiModelProperty(value = "更新时间")
    private Integer updateDate;


}
