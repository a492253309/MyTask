package com.shaohao.mytask.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.shaohao.mytask.entity.TaskConfig;
import com.shaohao.mytask.mapper.TaskConfigMapper;
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
import java.util.List;
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

    @Autowired
    private MyTaskService myTaskService;
    @Autowired
    private TaskConfigMapper taskConfigMapper;



    @Scheduled(fixedRate=660000)
    public void doTask() {
        main();
    }

    public void main() {
        List<TaskConfig> list = taskConfigMapper.selectList(new QueryWrapper<>());
        for (TaskConfig entity: list){
            myTaskService.main(entity);
        }
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
