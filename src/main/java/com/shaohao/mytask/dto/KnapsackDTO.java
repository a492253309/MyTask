package com.shaohao.mytask.dto;

import lombok.Data;

@Data
public class KnapsackDTO {
    /**
     * 101：叶子 301：刷新券 2006：南瓜种子
     */
    private Integer item_id;
    /**
     * count
     */
    private double count;
}
