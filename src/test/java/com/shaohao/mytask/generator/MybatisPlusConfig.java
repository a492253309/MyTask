package com.shaohao.mytask.generator;

/**
 * Mybatis-plus 自动生成文件配置
 * 这是swagger的
 */

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.Scanner;

public class MybatisPlusConfig {
    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (ipt != null) {
                return ipt;
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
    public static void main(String[] args) {
// 创建代码生成器对象
        AutoGenerator mpg = new AutoGenerator();
// 全局配置
        GlobalConfig gc = new GlobalConfig();
        //默认定位到的当前用户目录("user.dir")（即工程根目录）
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("shaohao");
//生成之后是否打开资源管理器
        gc.setOpen(false);
//重新生成时是否覆盖文件
        gc.setFileOverride(false);
//%s 为占位符
//mp生成service层代码,默认接口名称第一个字母是有I
        gc.setServiceName("%sService");
//设置主键生成策略 自动增长
        gc.setIdType(IdType.AUTO);
//设置Date的类型 只使用 java.util.date 代替
        gc.setDateType(DateType.ONLY_DATE);
//开启实体属性 Swagger2 注解
        gc.setSwagger2(true);
        mpg.setGlobalConfig(gc);
// 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://sh-cynosdbmysql-grp-g1ebxqky.sql.tencentcdb.com:25934/tgbot?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&serverTimezone=GMT&useSSL=false");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("Tt667788");
//使用mysql数据库
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);
// 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("请输入模块名"));
        pc.setParent("com.shaohao.mytask");
        pc.setController("controller");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        pc.setMapper("mapper");
        pc.setEntity("entity");
        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
// 策略配置
        StrategyConfig strategy = new StrategyConfig();
//设置哪些表需要自动生成
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//实体类名称驼峰命名
        strategy.setNaming(NamingStrategy.underline_to_camel);
//列名名称驼峰命名
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//使用简化getter和setter

        strategy.setEntityLombokModel(true);
//设置controller的api风格 使用RestController
        strategy.setRestControllerStyle(true);
//驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        //忽略表中生成实体类的前缀
//        strategy.setTablePrefix("wf");
        mpg.setStrategy(strategy);
        mpg.execute();
    }
}
