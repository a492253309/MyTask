package com.shaohao.mytask.dto;

import lombok.Data;

@Data
public class ShopItemsDTO {
    /**
     * 101：叶子 301：刷新券 2006：南瓜种子
     */
    private Integer item_id;
    /**
     * count
     */
    private Integer count;

    private Integer shop_inventory;
    private Integer unlock_at;
    private Integer begin_sale_at;
    private Long emd_sale_at;
}
