package com.shaohao.mytask.service;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.shaohao.mytask.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
//import java.security.KeyStore;
import java.time.Duration;
import java.time.Instant;
import java.util.Scanner;

/**
 * <p>
 * 友情链接 服务实现类
 * </p>
 *
 * @author shaohao
 * @since 2023-06-17
 */
@Service
public class MyTask {


    private static final Logger logger = LoggerFactory.getLogger(MyTask.class);

//    @Value("${task.game.token}")
    private String token ;


    @Autowired
    private MyGameService myGameService;

    @Autowired
    private GameActionService gameActionService;


    @Autowired
    private MyTaskService myTaskService;


    //轮次
    private int Round = 1;;

    private String wallet = "false";
    private  int item_id = 0;
    private  boolean sellFlag = true;
    private  boolean ksFlag = true;
    private  boolean wkFlag = true;


    @Scheduled(fixedRate=660000)
    public void doTask() {

//        String TOKEN = scanner("token");
//        System.out.println("token");
        main();

    }

    public void main() {
        if(Round == 1){
            while ("false".equals(wallet)){
                token = scanner("token");
                wallet = myGameService.do_verify(token);
            }
//            sleep(1);
            item_id = scannerInt("农作物id");
            String sell_flag = scanner("是否卖出农作物?(Y/N)");
            if ("N".equals(sell_flag)){
                sellFlag = false;
            }
            String ks_flag = scanner("是否砍树?(Y/N)");
            if ("N".equals(ks_flag)){
                ksFlag = false;
            }
            String wk_flag = scanner("是否挖矿?(Y/N)");
            if ("N".equals(wk_flag)){
                wkFlag = false;
            }

        }

//        myGameService.do_main(token,wallet);
        //item_id 种子
        myGameService.do_farm(token,wallet,item_id,sellFlag);
        if (ksFlag){
            myGameService.do_collect( token, wallet,10);
        }
        if (wkFlag){
//            myGameService.do_collect( token, wallet,20);
        }

        Round ++;

    }


    /**
     * <p>
     * 读取控制台字符串
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        String ipt = "";
        boolean validInput = false;
        while (!validInput) {
            StringBuilder help = new StringBuilder();
            help.append("请输入" + tip + "：");
            System.out.println(help.toString());
            if (scanner.hasNext()) {
                ipt = scanner.nextLine();
                if (ipt != null) {
                    validInput = true;
                }
            }
            if (!validInput){
                System.out.println("请重新输入" + tip + "：");
            }

        }
//        scanner.close();
        return ipt;
    }
    /**
     * <p>
     * 读取控制台int
     * </p>
     */
    public static int scannerInt(String tip) {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        boolean validInput = false;
        while (!validInput) {
            System.out.print("请输入" + tip + "：");
            String input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("输入不能为空，请重新输入。");
                continue;
            }

            try {
                number = Integer.parseInt(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("输入无效，请重新输入。");
            }
        }
//        scanner.close();
        return number;
    }

    public void test() {

        int second = 1687971235;
        int time =DateUtils.currentSecond();
        System.out.println("开始：" +time);
        if(time>second) {
//            temp();
            Instant instant = Instant.ofEpochSecond(second);
            Duration duration = Duration.ofHours(2);
            Instant result = instant.plus(duration);
            second = (int) result.getEpochSecond();
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
