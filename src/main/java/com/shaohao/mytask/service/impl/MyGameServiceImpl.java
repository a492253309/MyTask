package com.shaohao.mytask.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shaohao.mytask.entity.*;
import com.shaohao.mytask.mapper.*;
import com.shaohao.mytask.service.*;
import com.shaohao.mytask.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class MyGameServiceImpl implements MyGameService {

    @Autowired
    private GameActionService gameAction;
    @Autowired
    private WorldFairyService wofService;
    @Autowired
    private LandMapper landMapper;

    @Autowired
    private KnapsackItemMapper knapsackItemMapper;

    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private OreMapper oreMapper;
    @Autowired
    private ItemInfoMapper itemInfoMapper;
    @Autowired
    private SaleShopMapper saleShopMapper;

    private static final Logger logger = LoggerFactory.getLogger("MyLogger");


    @Override
    public String do_verify(String token) {
        return gameAction.VerifyToken(token);
    }

    @Override
    public void do_main(String token,String wallet) {

        //item_id 种子
        do_farm(token,wallet,2009,true);
        do_collect( token, wallet,10);

    }

    @Override
    public void do_farm(String token,String wallet, int item_id,boolean sellFlag) {
        sleep(1);
        if(!gameAction.Get_Goods(token,wallet)){
            logger.error("收菜失败");
            return;
        }
        if(sellFlag){
            sleep(2);
            if(!gameAction.Sell_Goods(token,wallet,item_id-1000)){
                logger.error("卖菜失败");
                return;
            }
        }

        sleep(2);
        QueryWrapper<Land> query = new QueryWrapper<>();
        query.eq("address", wallet);
        List<Land> landList = landMapper.selectList(query);
        int need_count = 0;
        for (Land land: landList){
            if (land.getSeedId() == 0){
                need_count++;
            }
        }
        if(need_count>0){
            QueryWrapper<KnapsackItem> wrapper = new QueryWrapper();
            wrapper.eq("address", wallet);
            wrapper.eq("item_id", item_id);
            KnapsackItem have_info = knapsackItemMapper.selectOne(wrapper);
            if (have_info.getIcount()<need_count){
                int buy_count = (int) (need_count- have_info.getIcount());
                QueryWrapper<Shop> shop_wrapper = new QueryWrapper();
                shop_wrapper.eq("address", wallet);
                shop_wrapper.eq("item_id", item_id);
                Shop shop = shopMapper.selectOne(shop_wrapper);
                if (shop.getCurrentNum()<buy_count){
                    if(shop.getCurrentNum() == 0){
                        if(!do_refresh(token, wallet,  item_id)){
                            return;
                        }
                        sleep(2);
                        if(!gameAction.Buy_Goods(token, wallet, item_id,buy_count)){
                            logger.error("买种子失败");
                            return;
                        }
                    }else{
                        if(!gameAction.Buy_Goods(token, wallet, item_id,shop.getCurrentNum())){
                            logger.error("买种子失败");
                            return;
                        }
                        sleep(1);
                        if(!do_refresh(token, wallet,  item_id)){
                            return;
                        }
                        sleep(2);
                        if(!gameAction.Buy_Goods(token, wallet, item_id,buy_count-shop.getCurrentNum())){
                            logger.error("买种子失败");
                            return;
                        }
                    }
                }else{
                    sleep(2);
                    if(!gameAction.Buy_Goods(token, wallet, item_id,buy_count)){
                        logger.error("买种子失败");
                        return;
                    }
                }
            }
            sleep(1);
            if(!gameAction.Into_Goods(token,wallet,item_id)){
                logger.error("种菜失败");
                return;
            }
        }

    }


    public boolean do_refresh(String token,String wallet, int item_id) {
        QueryWrapper<KnapsackItem> wrapper301 = new QueryWrapper();
        wrapper301.eq("address", wallet);
        wrapper301.eq("item_id", 301);
        KnapsackItem have301 = knapsackItemMapper.selectOne(wrapper301);
        if (have301.getIcount()>0){
            if(!gameAction.Refresh_Shop(token, wallet, item_id)){
                logger.error("刷新失败");
                return false;
            }
        }else {
            logger.error("刷新券没了，请购买刷新券");
            return false;
        }
        return true;
    }

    //type 10:砍树 20：挖矿
    @Override
    public void do_collect(String token,String wallet,int type) {
        int item_id =0;
        if (type == 10){
            item_id = 4001;
        }else if(type == 20){
            item_id = 4002;
        }
        if (item_id == 0){
            return;
        }
        QueryWrapper<Ore> query = new QueryWrapper<>();
        query.eq("address", wallet);
        query.eq("type", type);
        List<Ore> oreList = oreMapper.selectList(query);
        for (Ore ore : oreList){
             if(DateUtils.currentSecond()>ore.getUnlockAt()){
                 QueryWrapper<KnapsackItem> have_query = new QueryWrapper();
                 have_query.eq("address", wallet);
                 have_query.eq("item_id", item_id);
                 KnapsackItem have_info = knapsackItemMapper.selectOne(have_query);
                 if (have_info.getIcount() == 0){
                     QueryWrapper<Shop> shop_wrapper = new QueryWrapper();
                     shop_wrapper.eq("address", wallet);
                     shop_wrapper.eq("item_id", item_id);
                     Shop shop = shopMapper.selectOne(shop_wrapper);
                     if (shop.getCurrentNum()==0){
                         if(!do_refresh(token, wallet,  item_id)){
                             return;
                         }
                     }
                     if(!gameAction.Buy_Goods(token, wallet, item_id,1)){
                         logger.error("买铁具失败");
                         return;
                     }
                 }
                 if(!gameAction.Collect_Mining(token, wallet, ore.getOreId())){
                     logger.error("do_collect失败");
                     return;
                 }
                 logger.info("挖矿："+ore.getOreName() + ",成功");
                 sleep(1);
             }else{
                 logger.info("挖矿等待中," + ore.getOreName()+",成熟时间:" + DateUtils.getStringDateByUnix(ore.getUnlockAt()));
             }
        }

    }

    public void sleep(int second) {
        try {
            Thread.sleep(1000*second); // 休眠十秒（10000毫秒）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
