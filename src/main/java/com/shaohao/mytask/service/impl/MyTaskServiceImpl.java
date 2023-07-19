package com.shaohao.mytask.service.impl;

import com.shaohao.mytask.entity.TaskConfig;
import com.shaohao.mytask.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 友情链接 服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2023-06-17
 */
@Service
public class MyTaskServiceImpl implements MyTaskService {

    @Autowired
    private MyGameService myGameService;

    @Autowired
    private TaskConfigService taskConfigService;

    @Override
    public void main(TaskConfig entity) {
        boolean sellFlag = true;
        boolean ksFlag = true;
        boolean wkFlag = true;
        String token = entity.getToken();
        String wallet = taskConfigService.getWallet(token);
        if(entity.getRound() == 1){
            wallet = myGameService.do_verify(token);
            if (wallet.equals("false")){
                return;
            }
            taskConfigService.setWallet(token,wallet);
        }
        if ("N".equals(entity.getSellFlag())){
            sellFlag = false;
        }
        if ("N".equals(entity.getKsFlag())){
            ksFlag = false;
        }
        if ("N".equals(entity.getWkFlag())){
            wkFlag = false;
        }
        //item_id 种子
        myGameService.do_farm(token,wallet,entity.getItemId(),sellFlag);
        if (ksFlag){
            myGameService.do_collect( token, wallet,10);
        }
        if (wkFlag){
//            myGameService.do_collect( token, wallet,20);
        }
        taskConfigService.setRound(wallet);

    }



}
