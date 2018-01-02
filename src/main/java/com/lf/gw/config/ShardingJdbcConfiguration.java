package com.lf.gw.config;

import com.zaxxer.hikari.HikariDataSource;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


@Configuration
@EnableConfigurationProperties({ShardingJdbcProperties.class})
public class ShardingJdbcConfiguration {

    private final Logger log = LoggerFactory.getLogger(ShardingJdbcConfiguration.class);
    @Bean
   public DataSource dataSource(ShardingJdbcProperties shardingJdbcProperties)  throws SQLException {

        Map<String,DataSource> dataSourceMap=new HashMap<>();

        Properties dataSourceProperties = new Properties();
        dataSourceProperties.setProperty("cachePrepStmts","true");
        dataSourceProperties.setProperty("prepStmtCacheSize","250");
        dataSourceProperties.setProperty("prepStmtCacheSqlLimit","2048");
        dataSourceProperties.setProperty("useServerPrepStmts","true");

        HikariDataSource main = new HikariDataSource();
        main.setDriverClassName("com.mysql.jdbc.Driver");
        main.setJdbcUrl("jdbc:mysql://192.168.36.109:3306/main?useUnicode=true&characterEncoding=utf8&useSSL=false");
        main.setPassword("123456");
        main.setUsername("root");
        main.setDataSourceProperties(dataSourceProperties);
        dataSourceMap.put("main",main);

        HikariDataSource b1Main = new HikariDataSource();
        b1Main.setDriverClassName("com.mysql.jdbc.Driver");
        b1Main.setJdbcUrl("jdbc:mysql://192.168.36.109:3306/b1_main?useUnicode=true&characterEncoding=utf8&useSSL=false");
        b1Main.setPassword("123456");
        b1Main.setUsername("root");
        b1Main.setDataSourceProperties(dataSourceProperties);
        dataSourceMap.put("b1_main",b1Main);

        HikariDataSource b1Db1 = new HikariDataSource();
        b1Db1.setDriverClassName("com.mysql.jdbc.Driver");
        b1Db1.setJdbcUrl("jdbc:mysql://192.168.36.109:3306/b1_db1?useUnicode=true&characterEncoding=utf8&useSSL=false");
        b1Db1.setUsername("root");
        b1Db1.setPassword("123456");
        b1Db1.setDataSourceProperties(dataSourceProperties);
        dataSourceMap.put("b1_db1",b1Db1);

        HikariDataSource b1Db2 = new HikariDataSource();
        b1Db2.setDriverClassName("com.mysql.jdbc.Driver");
        b1Db2.setJdbcUrl("jdbc:mysql://192.168.36.109:3306/b1_db2?useUnicode=true&characterEncoding=utf8&useSSL=false");
        b1Db2.setUsername("root");
        b1Db2.setPassword("123456");
        b1Db2.setDataSourceProperties(dataSourceProperties);
        dataSourceMap.put("b1_db2",b1Db2);

        HikariDataSource b2Main = new HikariDataSource();
        b2Main.setDriverClassName("com.mysql.jdbc.Driver");
        b2Main.setJdbcUrl("jdbc:mysql://192.168.36.109:3306/b2_main?useUnicode=true&characterEncoding=utf8&useSSL=false");
        b2Main.setPassword("123456");
        b2Main.setUsername("root");
        b2Main.setDataSourceProperties(dataSourceProperties);
        dataSourceMap.put("b2_main",b2Main);

        HikariDataSource b2Db1 = new HikariDataSource();
        b2Db1.setDriverClassName("com.mysql.jdbc.Driver");
        b2Db1.setJdbcUrl("jdbc:mysql://192.168.36.109:3306/b2_db1?useUnicode=true&characterEncoding=utf8&useSSL=false");
        b2Db1.setUsername("root");
        b2Db1.setPassword("123456");
        b2Db1.setDataSourceProperties(dataSourceProperties);
        dataSourceMap.put("b2_db1",b2Db1);

        HikariDataSource b2Db2 = new HikariDataSource();
        b2Db2.setDriverClassName("com.mysql.jdbc.Driver");
        b2Db2.setJdbcUrl("jdbc:mysql://192.168.36.109:3306/b2_db2?useUnicode=true&characterEncoding=utf8&useSSL=false");
        b2Db2.setUsername("root");
        b2Db2.setPassword("123456");
        b2Db2.setDataSourceProperties(dataSourceProperties);
        dataSourceMap.put("b2_db2",b2Db2);

        //table project_info
        TableRuleConfiguration projectInfoTableRuleConfig = new TableRuleConfiguration();
        projectInfoTableRuleConfig.setLogicTable("project_info");
        projectInfoTableRuleConfig.setActualDataNodes("b1_main.project_info");

        //table project_transaction_record
        TableRuleConfiguration projectTransactionRecordTableRuleConfig = new TableRuleConfiguration();
        projectTransactionRecordTableRuleConfig.setLogicTable("project_transaction_record");
        projectTransactionRecordTableRuleConfig.setActualDataNodes("b1_main.project_transaction_record_main${0..1}");
        projectTransactionRecordTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("transaction_year","project_transaction_record_main${transaction_year % 2}"));
        //projectTransactionRecordTableRuleConfig.setKeyGeneratorColumnName("id");

        //table shop_info
        TableRuleConfiguration shopInfoTableRuleConfig = new TableRuleConfiguration();
        shopInfoTableRuleConfig.setLogicTable("shop_info");
        shopInfoTableRuleConfig.setActualDataNodes("b1_db${1..2}.shop_info");
        shopInfoTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("project_id","b1_db${project_id % 2 +1}"));
        //shopInfoTableRuleConfig.setKeyGeneratorColumnName("id");

        //table sale_amt1
        TableRuleConfiguration saleAmt1TableRuleConfig = new TableRuleConfiguration();
        saleAmt1TableRuleConfig.setLogicTable("sale_amt1");
        saleAmt1TableRuleConfig.setActualDataNodes("b1_db${1..2}.sale_amt1_${[0,1]}");
        saleAmt1TableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("project_id","b1_db${project_id % 2 + 1}"));
        saleAmt1TableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("project_id","sale_amt1_${(project_id % 2 == 0)?(project_id % 4==0?0:1):(project_id % 4 ==1?0:1)}"));
        //saleAmt1TableRuleConfig.setKeyGeneratorColumnName("id");

        //table sale_amt2
        TableRuleConfiguration saleAmt2TableRuleConfig = new TableRuleConfiguration();
        saleAmt2TableRuleConfig.setLogicTable("sale_amt2");
        saleAmt2TableRuleConfig.setActualDataNodes("b1_db${1..2}.sale_amt2_${[0,1]}");
        saleAmt2TableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("project_id","b1_db${project_id % 2 +1}"));
        saleAmt2TableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("shop_id","sale_amt2_${shop_id % 2}"));
        //saleAmt2TableRuleConfig.setKeyGeneratorColumnName("id");



        ShardingRuleConfiguration shardingRuleConfig= new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("main");
        shardingRuleConfig.getTableRuleConfigs().add(projectInfoTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(projectTransactionRecordTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(shopInfoTableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(saleAmt1TableRuleConfig);
        shardingRuleConfig.getTableRuleConfigs().add(saleAmt2TableRuleConfig);

        Properties props = new Properties();
        props.setProperty("sql.show","true");

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap<String, Object>(),props);
   }

}
