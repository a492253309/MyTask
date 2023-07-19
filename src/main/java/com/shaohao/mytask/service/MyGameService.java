package com.shaohao.mytask.service;

public interface MyGameService {


    String do_verify(String token);
    /**
     * 验证并自动更新起源岛
     *
     * @return Boolean
     */
    void do_main(String token,String wallet);
    /**
     * 自动种植
     *
     * @return Boolean
     */
    void do_farm(String token,String wallet, int item_id,boolean sellFlag);
    /**
     * 自动挖矿
     *
     * @return Boolean
     */
    void do_collect(String token,String wallet,int type);



}
