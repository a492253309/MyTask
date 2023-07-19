package com.shaohao.mytask.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.google.common.collect.Maps;
import com.shaohao.mytask.dto.KnapsackDTO;
import com.shaohao.mytask.dto.LandDTO;
import com.shaohao.mytask.dto.OreDTO;
import com.shaohao.mytask.dto.ShopItemsDTO;
import com.shaohao.mytask.entity.*;
import com.shaohao.mytask.enums.wofUrlEnum;
import com.shaohao.mytask.mapper.*;
import com.shaohao.mytask.service.GameActionService;
import com.shaohao.mytask.service.WorldFairyService;
import com.shaohao.mytask.utils.DateUtils;
import com.shaohao.mytask.utils.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2023-06-17
 */
@Service
public class GameActionServiceImpl implements GameActionService {

    @Autowired
    private HTTP http;

    @Autowired
    private WorldFairyService wofService;

    @Autowired
    private WorldFairyMapper wofMapper;

    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private LandMapper landMapper;
    @Autowired
    private OreMapper oreMapper;
    @Autowired
    private KnapsackItemMapper knapsackItemMapper;
    @Autowired
    private ItemInfoMapper itemInfoMapper;
    @Autowired
    private SaleShopMapper saleShopMapper;



    private static final Logger logger = LoggerFactory.getLogger("MyLogger");
    private static final Logger  myLogger = LoggerFactory.getLogger(GameActionServiceImpl.class);


    @Override
    public boolean test(String token, String wallet) {
        return false;
    }

    @Override
    public String VerifyToken(String token) {

        logger.info("----------------------------");
        try {
            String result = http.GET(wofUrlEnum.Verify_User.getUrl(), token);
            myLogger.info("获取用户信息Verify_User：" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.getInteger("code") != 0) {
                logger.error("Verify_User获取用户错误");
                return "false";
            }
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSONObject.parseObject(data);
            String wallet = dataJson.getString("wallet");
            String base = dataJson.getString("base");
            updateWorld(token, wallet, base);
            VerifyInventory(token, wallet);
            String item = dataJson.getString("item");
            updateItem(wallet, item);
            String land = dataJson.getString("land");
            updateLand(wallet, land);
            String ore = dataJson.getString("ore");
            updateOre(wallet, ore);
//            Integer time =landList.stream().map(Land::getHarvest_at).max(Integer::compareTo).get();
//            String get_time = DateUtils.getStringDateByUnix(time);
//            String item = dataJson.getString("item");
//            List<Knapsack> itemList = JSONArray.parseArray(item, Knapsack.class);
//            double coin_num =itemList.stream().filter(Knapsack -> Knapsack.getItem_id()== 101).mapToDouble(Knapsack::getCount).findFirst().orElse(0.0);
//            double refresh_num =itemList.stream().filter(Knapsack -> Knapsack.getItem_id()== 301).mapToDouble(Knapsack::getCount).findFirst().orElse(0);
//            double owner01_num =itemList.stream().filter(Knapsack -> Knapsack.getItem_id()== 2006).mapToDouble(Knapsack::getCount).findFirst().orElse(0);
//            double owner11_num =itemList.stream().filter(Knapsack -> Knapsack.getItem_id()== 1006).mapToDouble(Knapsack::getCount).findFirst().orElse(0);
            logger.info("钱包地址:"+wallet);
//            logger.info("成熟时间:"+get_time +",叶子数量:"+coin_num +",刷新券数量:"+refresh_num +",南瓜种子数量:"+owner01_num + ",南瓜数量:"+owner11_num);


            return wallet;
        } catch (IOException e) {
            logger.error("Verify_User请求异常");
            e.printStackTrace();
            return "false";
        }
    }

    @Override
    public void init(String wallet,int farm_level) {


    }
    public void updateWorld(String token, String wallet, String base) {
        JSONObject baseJson = JSONObject.parseObject(base);
        WorldFairy worldFairy = new WorldFairy();
        worldFairy.setAddress(wallet);
        worldFairy.setUid(baseJson.getInteger("uid"));
        worldFairy.setToken(token);
        worldFairy.setLandNum(baseJson.getInteger("land_num"));
        worldFairy.setFarmLevel(baseJson.getInteger("farm_level"));
        worldFairy.setCollectLevel(baseJson.getInteger("collect_level"));
        worldFairy.setRanchLevel(baseJson.getInteger("ranch_level"));
        worldFairy.setCreateDate(DateUtils.currentSecond());
        QueryWrapper<WorldFairy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("address", wallet);
        List<WorldFairy> list = wofService.queryList(queryWrapper);
        if (list.isEmpty()) {
            worldFairy.setCreateDate(DateUtils.currentSecond());
            if (!wofService.insert(worldFairy)) {
                logger.error("插入错误，岛屿:" + worldFairy);
            }
        } else {
            UpdateWrapper updateWrapper = new UpdateWrapper();
            updateWrapper.eq("address", wallet);
            worldFairy.setUpdateDate(DateUtils.currentSecond());
            if (!wofService.update(updateWrapper, worldFairy)) {
                logger.error("更新错误，岛屿:" + worldFairy);
            }
        }
        logger.info("土地数:" + worldFairy.getLandNum() +"农场等级:"+worldFairy.getFarmLevel() +"采集等级:"+worldFairy.getCollectLevel());
    }

    public void updateLand(String wallet, String land) {
        List<LandDTO> landList = JSONArray.parseArray(land, LandDTO.class);
        for (LandDTO dto : landList) {
            if (dto.getSeed_id() == 0){
                continue;
            }
            Land entity = new Land().setAddress(wallet).setLandId(dto.getLand_id())
                    .setSeedId(dto.getSeed_id()).setHarvestAt(dto.getHarvest_at()).setUpdateDate(DateUtils.currentSecond());
            QueryWrapper<ItemInfo> query = new QueryWrapper();
            query.eq("item_id", dto.getSeed_id());
            ItemInfo itemInfo = itemInfoMapper.selectOne(query);
            entity.setSeedName(itemInfo.getItemName());
            QueryWrapper<Land> wrapper = new QueryWrapper();
            wrapper.eq("address", wallet);
            wrapper.eq("land_id", dto.getLand_id());
            if (landMapper.selectOne(wrapper) == null) {
                landMapper.insert(entity);
            } else {
                UpdateWrapper<Land> uwrapper = new UpdateWrapper();
                uwrapper.eq("address", wallet);
                uwrapper.eq("land_id", dto.getLand_id());
                landMapper.update(entity, uwrapper);
            }
        }
    }

    public void updateOre(String wallet, String ore) {
        List<OreDTO> oreList = JSONArray.parseArray(ore, OreDTO.class);
        Map<Integer,String> oreMap = getOreMap();
        for (OreDTO dto : oreList) {
            Ore entity = new Ore().setAddress(wallet).setOreId(dto.getOre_id()).setOreName(oreMap.get(dto.getOre_id()))
                    .setUnlockAt(dto.getUnlock_at()).setUpdateDate(DateUtils.currentSecond());
            QueryWrapper<Ore> wrapper = new QueryWrapper();
            wrapper.eq("address", wallet);
            wrapper.eq("ore_id", dto.getOre_id());
            if (oreMapper.selectOne(wrapper) == null) {
                oreMapper.insert(entity);
            } else {
                UpdateWrapper<Ore> uwrapper = new UpdateWrapper();
                uwrapper.eq("address", wallet);
                uwrapper.eq("ore_id", dto.getOre_id());
                oreMapper.update(entity, uwrapper);
            }
        }
    }

    public void updateItem(String wallet, String item) {
        List<KnapsackDTO> itemList = JSONArray.parseArray(item, KnapsackDTO.class);
        for (KnapsackDTO jsonItem : itemList) {
            KnapsackItem entity = new KnapsackItem().setAddress(wallet).setItemId(jsonItem.getItem_id())
                    .setIcount(jsonItem.getCount()).setCreateDate(DateUtils.currentSecond());
            QueryWrapper<KnapsackItem> wrapper = new QueryWrapper();
            wrapper.eq("address", wallet);
            wrapper.eq("item_id", jsonItem.getItem_id());
            if (knapsackItemMapper.selectOne(wrapper) == null) {
                knapsackItemMapper.insert(entity);
            } else {
                UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
                uwrapper.eq("address", wallet);
                uwrapper.eq("item_id", jsonItem.getItem_id());
                knapsackItemMapper.update(entity, uwrapper);
            }
        }
    }

    @Override
    public boolean VerifyInventory(String token, String wallet) {
        try {
            String result = http.GET(wofUrlEnum.Shop_Inventory.getUrl(), token);
            myLogger.info("获取商店库存Shop_Inventory：" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.getInteger("code") != 0) {
                logger.error("Shop_Inventory返回错误");
                logger.info(result);
                return false;
            }
            String data = jsonObject.getString("data");
            List<ShopItemsDTO> shopList = JSONArray.parseArray(data, ShopItemsDTO.class);
            for (ShopItemsDTO item : shopList) {
                Shop shop = new Shop().setAddress(wallet).setCurrentNum(item.getCount()).setItemId(item.getItem_id())
                        .setMaxNum(item.getShop_inventory()).setCreateDate(DateUtils.currentSecond());
                QueryWrapper<Shop> wrapper = new QueryWrapper();
                wrapper.eq("address", wallet);
                wrapper.eq("item_id", item.getItem_id());
                if (shopMapper.selectOne(wrapper) == null) {
                    shopMapper.insert(shop);
                } else {
                    UpdateWrapper<Shop> uwrapper = new UpdateWrapper();
                    uwrapper.eq("address", wallet);
                    uwrapper.eq("item_id", item.getItem_id());
                    shopMapper.update(shop, uwrapper);
                }
            }
            return true;
        } catch (IOException e) {
            logger.error("Shop_Inventory请求异常");
            e.printStackTrace();
            return false;
        }
    }
    public Map<Integer,String> getOreMap() {
        Map<Integer,String> oreMap = new HashMap<>();
        oreMap.put(3000, "树5");
        oreMap.put(3001, "树1");
        oreMap.put(3002, "树2");
        oreMap.put(3003, "树3");
        oreMap.put(3004, "树4");
        oreMap.put(3005, "矿产");
        return oreMap;
    }

    @Override
    public boolean Get_Goods(String token,String wallet) {
        QueryWrapper<Land> query = new QueryWrapper<>();
        query.eq("address", wallet);
        List<Land> landList = landMapper.selectList(query);
        for (Land land: landList){
            if (land.getSeedId() == 0){
                continue;
            }
            if (DateUtils.currentSecond()>= land.getHarvestAt()){
                Map<String, Object> params = Maps.newHashMap();
                params.put("land_id", land.getLandId());
                params.put("seed_id", land.getSeedId());
                String body = JSONObject.toJSONString(params);
                logger.info("----------------------------");
                try {
                    String result = http.postJson(wofUrlEnum.Get_Goods.getUrl(),body,token);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    if(jsonObject.getInteger("code") != 0){
                        logger.error("收菜："+land.getLandId()+","+ land.getSeedName()+",失败," +"Get_Goods返回错误");
                        logger.info(result);
                        return false;
                    }
                } catch (
                        IOException e) {
                    logger.error("收菜："+land.getLandId()+","+ land.getSeedName()+",失败," +"Get_Goods请求异常");
                    e.printStackTrace();
                    return false;
                }
                int item_id = land.getSeedId() -1000;
                QueryWrapper<KnapsackItem> wrapper = new QueryWrapper();
                wrapper.eq("address", wallet);
                wrapper.eq("item_id", item_id);
                KnapsackItem entity = knapsackItemMapper.selectOne(wrapper);
                entity.setIcount(entity.getIcount()+1);
                    UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
                    uwrapper.eq("address", wallet);
                    uwrapper.eq("item_id", item_id);
                    knapsackItemMapper.update(entity, uwrapper);
                logger.info("收菜："+land.getLandId()+","+ land.getSeedName()+",成功");
                UpdateWrapper<Land> uland = new UpdateWrapper();
                uland.eq("address",wallet);
                uland.eq("land_id",land.getLandId());
                land.setSeedId(0);
                land.setHarvestAt(0);
                landMapper.update(land,uland);
                sleep(1);
            }else{
                logger.info("等待中" + land.getLandId()+",成熟时间:" + DateUtils.getStringDateByUnix(land.getHarvestAt()));
            }
        }
        return true;
    }

    @Override
    public boolean Sell_Goods(String token, String wallet, int item_id) {
        QueryWrapper<KnapsackItem> wrapper = new QueryWrapper();
        wrapper.eq("address", wallet);
        wrapper.eq("item_id", item_id);
        KnapsackItem entity = knapsackItemMapper.selectOne(wrapper);
        int count = (int) entity.getIcount();
        if (count == 0){
            logger.info("拥有0个"+ entity.getItemName()+"，Sell_Goods直接返回");
            return true;
        }
        Map<String, Object> params = Maps.newHashMap();
        params.put("item_id", item_id);
        params.put("count", count);
        String body = JSONObject.toJSONString(params);
        try {
            String result = http.postJson(wofUrlEnum.Sell_Goods.getUrl(),body,token);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject.getInteger("code") != 0){
                logger.error("卖出：" + entity.getItemName()+entity.getIcount()+"个,失败," +"Sell_Goods返回错误");
                logger.info(result);
                return false;
            }
        } catch (
                IOException e) {
            logger.error("卖菜："+ entity.getItemName()+entity.getIcount()+"个,失败," +"Sell_Goods请求异常");
            e.printStackTrace();
            return false;
        }
        logger.info("卖菜："+ entity.getItemName()+entity.getIcount()+"个,成功");
        UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
        uwrapper.eq("address", wallet);
        uwrapper.eq("item_id", item_id);
        entity.setIcount(0);
        knapsackItemMapper.update(entity, uwrapper);
        QueryWrapper<SaleShop> query = new QueryWrapper();
        query.eq("address", wallet);
        query.eq("item_id", item_id);
        SaleShop saleShop = saleShopMapper.selectOne(query);
        wrapper.clear(); // 清空条件
        wrapper.eq("address", wallet);
        wrapper.eq("item_id", 101);
        entity = knapsackItemMapper.selectOne(wrapper);
        entity.setIcount(entity.getIcount()+(saleShop.getPrice()*count));
        uwrapper.clear(); // 清空条件
        uwrapper.eq("address", wallet);
        uwrapper.eq("item_id", 101);
        knapsackItemMapper.update(entity, uwrapper);
        return true;
    }

    @Override
    public boolean Buy_Goods(String token, String wallet, int item_id,int count) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("item_id", item_id);
        params.put("count", count);
        String body = JSONObject.toJSONString(params);
        QueryWrapper<Shop> queryShop = new QueryWrapper<>();
        queryShop.eq("address",wallet);
        queryShop.eq("item_id",item_id);
        Shop shopInfo = shopMapper.selectOne(queryShop);
        try {
            String result = http.postJson(wofUrlEnum.Buy_Goods.getUrl(),body,token);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject.getInteger("code") != 0){
                logger.error("买："+shopInfo.getItemName()+count+"个,失败," +"Buy_Goods返回错误");
                logger.info(result);
                return false;
            }
        } catch (
                IOException e) {
            logger.error("买："+shopInfo.getItemName()+count+"个,失败," +"Buy_Goods请求异常");
            e.printStackTrace();
            return false;
        }
        logger.info("买："+shopInfo.getItemName()+count+"个,成功");
        QueryWrapper<KnapsackItem> have_query = new QueryWrapper();
        have_query.eq("address", wallet);
        have_query.eq("item_id", item_id);
        KnapsackItem have_info = knapsackItemMapper.selectOne(have_query);
        UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
        uwrapper.eq("address", wallet);
        uwrapper.eq("item_id", item_id);
        have_info.setIcount(have_info.getIcount()+count);
        knapsackItemMapper.update(have_info, uwrapper);
        have_query.clear(); // 清空条件
        have_query.eq("address", wallet);
        have_query.eq("item_id", 101);
        have_info = knapsackItemMapper.selectOne(have_query);
        have_info.setIcount(have_info.getIcount()-(shopInfo.getPrice()*count));
        uwrapper.clear(); // 清空条件
        uwrapper.eq("address", wallet);
        uwrapper.eq("item_id", 101);
        knapsackItemMapper.update(have_info, uwrapper);
        UpdateWrapper<Shop> updateShop = new UpdateWrapper<>();
        updateShop.eq("address",wallet);
        updateShop.eq("item_id",item_id);
        shopInfo.setCurrentNum(shopInfo.getCurrentNum()-count);
        shopMapper.update(shopInfo,updateShop);
        return true;
    }

    @Override
    public boolean Into_Goods(String token, String wallet,int item_id) {
        QueryWrapper<Land> query = new QueryWrapper<>();
        query.eq("address", wallet);
        List<Land> landList = landMapper.selectList(query);
        for (Land land: landList){
            if (land.getSeedId() == 0){
                QueryWrapper<ItemInfo> itquery = new QueryWrapper();
                itquery.eq("item_id", item_id);
                ItemInfo itemInfo = itemInfoMapper.selectOne(itquery);
                Map<String, Object> params = Maps.newHashMap();
                params.put("land_id", land.getLandId());
                params.put("seed_id", item_id);
                String body = JSONObject.toJSONString(params);
                try {
                    String result = http.postJson(wofUrlEnum.Into_Goods.getUrl(),body,token);
                    JSONObject jsonObject = JSONObject.parseObject(result);
                    if(jsonObject.getInteger("code") != 0){
                        logger.error("种菜："+itemInfo.getItemName()+land.getLandId()+",失败," +"Into_Goods返回错误");
                        logger.info(result);
                        return false;
                    }
                } catch ( IOException e) {
                    logger.error("种菜："+itemInfo.getItemName()+land.getLandId()+",失败," +"Into_Goods请求异常");
                    e.printStackTrace();
                    return false;
                }
                logger.info("种菜："+itemInfo.getItemName()+land.getLandId()+",成功");
                sleep(1);
                UpdateWrapper<Land> uland = new UpdateWrapper();
                uland.eq("address",wallet);
                uland.eq("land_id",land.getLandId());
                Instant instant = Instant.ofEpochSecond(DateUtils.currentSecond());
                Duration duration = Duration.ofHours(itemInfo.getHours());
                Instant result = instant.plus(duration);
                int second = (int) result.getEpochSecond();
                land.setSeedId(item_id).setSeedName(itemInfo.getItemName()).setHarvestAt(second);
                landMapper.update(land,uland);
                QueryWrapper<KnapsackItem> have_query = new QueryWrapper();
                have_query.eq("address", wallet);
                have_query.eq("item_id", item_id);
                KnapsackItem have_info = knapsackItemMapper.selectOne(have_query);
                UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
                uwrapper.eq("address", wallet);
                uwrapper.eq("item_id", item_id);
                have_info.setIcount(have_info.getIcount()-1);
                knapsackItemMapper.update(have_info, uwrapper);
            }
        }
        return true;
    }

    @Override
    public boolean Refresh_Shop(String token,String wallet,int item_id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("item_id", item_id);
        String body = JSONObject.toJSONString(params);
        try {
            String result =http.postJson(wofUrlEnum.Refresh_Shop.getUrl(),body, token);
            logger.info("刷新商店库存Refresh_Shop：" + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if (jsonObject.getInteger("code") != 0) {
                logger.error("Refresh_Shop返回错误");
                logger.info(result);
                return false;
            }
        }catch (IOException e) {
            logger.error("Refresh_Shop请求异常");
            e.printStackTrace();
            return false;
        }
        QueryWrapper<KnapsackItem> have_query = new QueryWrapper();
        have_query.eq("address", wallet);
        have_query.eq("item_id", 301);
        KnapsackItem have_info = knapsackItemMapper.selectOne(have_query);
        UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
        uwrapper.eq("address", wallet);
        uwrapper.eq("item_id", 301);
        have_info.setIcount(have_info.getIcount()-1);
        knapsackItemMapper.update(have_info, uwrapper);
        QueryWrapper<Shop> queryShop = new QueryWrapper<>();
        queryShop.eq("address",wallet);
        queryShop.eq("item_id",item_id);
        Shop shopInfo = shopMapper.selectOne(queryShop);
        shopInfo.setCurrentNum(shopInfo.getMaxNum());
        UpdateWrapper<Shop> update_shop = new UpdateWrapper();
        update_shop.eq("address", wallet);
        update_shop.eq("item_id", item_id);
        shopMapper.update(shopInfo, update_shop);
        return true;
    }

    @Override
    public boolean Collect_Mining(String token,String wallet,Integer ore_id) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("ore_id", ore_id);
        String body = JSONObject.toJSONString(params);
        try {
            String result = http.postJson(wofUrlEnum.Collect.getUrl(),body,token);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject.getInteger("code") != 0){
                logger.error("Collect_Mining返回错误");
                logger.info(result);
                return false;
            }
            String data = jsonObject.getString("data");
            JSONObject dataJson = JSONObject.parseObject(data);
            String gain = dataJson.getString("gain");
            List<KnapsackDTO> itemList = JSONArray.parseArray(gain, KnapsackDTO.class);
            for (KnapsackDTO item : itemList){
                QueryWrapper<KnapsackItem> have_query = new QueryWrapper();
                have_query.eq("address", wallet);
                have_query.eq("item_id", item.getItem_id());
                KnapsackItem have_info = knapsackItemMapper.selectOne(have_query);
                UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
                uwrapper.eq("address", wallet);
                uwrapper.eq("item_id", item.getItem_id());
                have_info.setIcount(have_info.getIcount() +item.getCount());
                knapsackItemMapper.update(have_info, uwrapper);
            }

        } catch ( IOException e) {
            logger.error("Collect_Mining请求异常");
            e.printStackTrace();
            return false;
        }
        int item_id =0;
        int minutes = 0;
        if (ore_id == 3001 || ore_id == 3002 || ore_id == 3003 ||ore_id == 3004 || ore_id == 3000){
            item_id = 4001;
            minutes = 108;
        }else if(ore_id == 3005){
            item_id = 4002;
            minutes = 216;
        }
        if (item_id != 0){
            QueryWrapper<KnapsackItem> have_query = new QueryWrapper();
            have_query.eq("address", wallet);
            have_query.eq("item_id", item_id);
            KnapsackItem have_info = knapsackItemMapper.selectOne(have_query);
            UpdateWrapper<KnapsackItem> uwrapper = new UpdateWrapper();
            uwrapper.eq("address", wallet);
            uwrapper.eq("item_id", item_id);
            have_info.setIcount(have_info.getIcount()-1);
            knapsackItemMapper.update(have_info, uwrapper);
            QueryWrapper<Ore> query_ore = new QueryWrapper();
            query_ore.eq("address", wallet);
            query_ore.eq("ore_id", ore_id);
            Ore ore = oreMapper.selectOne(query_ore);
            int harvestAt = DateUtils.SecondAddMinutes(DateUtils.currentSecond(),minutes);
            ore.setUnlockAt(harvestAt);
            UpdateWrapper<Ore> uOre = new UpdateWrapper();
            uOre.eq("address",wallet);
            uOre.eq("ore_id",ore_id);
            oreMapper.update(ore,uOre);
        }
        return true;
    }

    public void sleep(int second) {
        try {
            Thread.sleep(1000*second); // 休眠十秒（10000毫秒）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
