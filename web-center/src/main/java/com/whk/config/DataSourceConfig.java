package com.whk.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.algorithm.core.config.AlgorithmConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.ReadwriteSplittingRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.rule.ReadwriteSplittingDataSourceRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.api.transaction.TransactionalReadQueryStrategy;
import org.apache.shardingsphere.single.api.config.SingleRuleConfiguration;
import org.apache.shardingsphere.single.api.constant.SingleTableConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

//@Configuration
public class DataSourceConfig {

//    @Bean
    public DataSource createDataSource() throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

// 配置第 1 个数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/admin");
        dataSource.setUsername("root");
        dataSource.setPassword("");
        dataSourceMap.put("ds0", dataSource);

// 配置第 2 个数据源
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://localhost:3306/admin");
        dataSource1.setUsername("root");
        dataSource1.setPassword("");
        dataSourceMap.put("ds1", dataSource1);

// 配置 t_order 表规则
        ReadwriteSplittingDataSourceRuleConfiguration orderTableRuleConfig =
                new ReadwriteSplittingDataSourceRuleConfiguration("readWriteDB", "ds0",
                        dataSourceMap.keySet().stream().toList(), TransactionalReadQueryStrategy.DYNAMIC, "random");

        Properties properties = new Properties();
        properties.put("type", "ROUND_ROBIN");
        AlgorithmConfiguration algorithmConfiguration = new AlgorithmConfiguration("random", properties);

        ReadwriteSplittingRuleConfiguration readwriteSplittingRuleConfiguration = new ReadwriteSplittingRuleConfiguration(Collections.singleton(orderTableRuleConfig), Map.of("random", algorithmConfiguration));

        SingleRuleConfiguration singleRuleConfiguration = new SingleRuleConfiguration();
        singleRuleConfiguration.setDefaultDataSource("ds0");
        singleRuleConfiguration.setTables(Collections.singleton(SingleTableConstants.ALL_TABLES));

// 创建 ShardingSphereDataSource
        return ShardingSphereDataSourceFactory.createDataSource(dataSourceMap, List.of(readwriteSplittingRuleConfiguration, singleRuleConfiguration), new Properties());
    }
}
