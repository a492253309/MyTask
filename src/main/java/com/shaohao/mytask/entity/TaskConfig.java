package com.shaohao.mytask.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 任务配置
 * </p>
 *
 * @author shaohao
 * @since 2023-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("wf_task_config")
public class TaskConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String token;

    private Integer itemId;

    private String sellFlag;

    private String ksFlag;

    private String wkFlag;

    private Integer createDate;

    private Integer createUser;

    private Integer updateDate;

    private Integer round;

    private String address;


}
