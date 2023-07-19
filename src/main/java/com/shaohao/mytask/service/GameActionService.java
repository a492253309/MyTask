package com.shaohao.mytask.service;

public interface GameActionService {


    boolean test(String token,String wallet);
    void init(String wallet,int farm_level);

    /**
     * 验证并自动更新起源岛
     *
     * @return Boolean
     */
    String VerifyToken(String token);

    /**
     * 自动更新库存
     *
     * @return Boolean
     */
    boolean VerifyInventory(String token,String wallet);

    /**
     * 收菜 单次
     *
     * @return Boolean
     */
    boolean Get_Goods(String token,String wallet);

    /**
     * 卖菜
     *
     * @return Boolean
     */
    boolean Sell_Goods(String token, String wallet, int item_id);

    /**
     * 买种子
     *
     * @return Boolean
     */
    boolean Buy_Goods(String token, String wallet, int item_id,int count);

    /**
     * 种菜 单次
     *
     * @return Boolean
     */
    boolean Into_Goods(String token,String wallet,int item_id);

    /**
     * 刷新商店库存
     *
     * @return Boolean
     */
    boolean Refresh_Shop(String token,String wallet,int item_id);

    /**
     * 收集_挖矿
     *
     * @return Boolean
     */
    boolean Collect_Mining(String token,String wallet,Integer ore_id);
}
