package com.whk.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.algorithm.core.config.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.mode.ModeConfiguration;
import org.apache.shardingsphere.infra.config.rule.RuleConfiguration;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepositoryConfiguration;
import org.apache.shardingsphere.readwritesplitting.config.ReadwriteSplittingRuleConfiguration;
import org.apache.shardingsphere.readwritesplitting.config.rule.ReadwriteSplittingDataSourceGroupRuleConfiguration;
import org.apache.shardingsphere.single.config.SingleRuleConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

//@Configuration
public class DataSourceConfig {
//    @Bean
    public DataSource createDataSource() throws SQLException {
        ModeConfiguration modeConfig = createModeConfiguration();// Build running mode
        Map<String, DataSource> dataSourceMap = createDataSources();
        Collection<RuleConfiguration> ruleConfigs = createRuleConfigs(); // Build specific rules
        Properties props = createProps(); // Build attribute configuration
        DataSource data = ShardingSphereDataSourceFactory.createDataSource("db_main", modeConfig, dataSourceMap, ruleConfigs, props);
        return data;
    }

    private ModeConfiguration createModeConfiguration() {
        return new ModeConfiguration("Standalone", new StandalonePersistRepositoryConfiguration("JDBC", new Properties()));
    }

    private Map<String, DataSource> createDataSources() {
        Map<String, DataSource> dataSourceMap = new HashMap<>();
        // Configure the 1st data source
        HikariDataSource dataSource1 = new HikariDataSource();
        dataSource1.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource1.setJdbcUrl("jdbc:mysql://localhost:3306/admin?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT");
        dataSource1.setUsername("root");
        dataSource1.setPassword("");
        dataSourceMap.put("ds_1", dataSource1);

        // Configure the 2nd data source
        HikariDataSource dataSource2 = new HikariDataSource();
        dataSource2.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource2.setJdbcUrl("jdbc:mysql://localhost:3306/admin?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&rewriteBatchedStatements=true&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT");
        dataSource2.setUsername("root");
        dataSource2.setPassword("");
        dataSourceMap.put("ds_2", dataSource2);
        return dataSourceMap;
    }

    private Collection<RuleConfiguration> createRuleConfigs() {
        // Configure readwrite-splitting rule
        List<RuleConfiguration> dataSourceGroups = new LinkedList<>();
        ReadwriteSplittingDataSourceGroupRuleConfiguration dataSourceConfig = new ReadwriteSplittingDataSourceGroupRuleConfiguration("readwrite_splitting_group",
                "ds_1", Arrays.asList("ds_1", "ds_2"), "weight_lb");
        Properties algorithmProps = new Properties();
        algorithmProps.setProperty("ds_1", "2");
        algorithmProps.setProperty("ds_2", "1");
        Map<String, AlgorithmConfiguration> algorithmConfigMap = new HashMap<>(1);
        algorithmConfigMap.put("weight_lb", new AlgorithmConfiguration("WEIGHT", algorithmProps));
        ReadwriteSplittingRuleConfiguration readwriteSplittingRuleConfig =new ReadwriteSplittingRuleConfiguration(Collections.singleton(dataSourceConfig), algorithmConfigMap);
        dataSourceGroups.add(readwriteSplittingRuleConfig) ;

        SingleRuleConfiguration ruleConfig = new SingleRuleConfiguration();
        ruleConfig.setDefaultDataSource("readwrite_splitting_group");
        ruleConfig.setTables(List.of("readwrite_splitting_group.*"));
        dataSourceGroups.add(ruleConfig);

        return dataSourceGroups;
    }

    private Properties createProps() {
        Properties props = new Properties();
        props.setProperty("sql-show", "true");
        props.setProperty("sql-simple", "true");
        return props;
    }
}
