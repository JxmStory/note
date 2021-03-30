package com.sh.dbsource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Date: 2018-11-29 11:52
 * @Author: micomo
 */

@Configuration
public class DataSourceConfig {

    // 主数据源
    @Primary
    @Bean(name = "master")
    @Qualifier("master")
    @ConfigurationProperties(prefix="spring.datasource.master")
    public DataSource getMasterSource(){
        return DataSourceBuilder.create().build();
    }

    // 从数据源
    @Bean(name = "slave")
    @Qualifier("slave")
    @ConfigurationProperties(prefix="spring.datasource.slave")
    public DataSource getSlaveSource(){
        return DataSourceBuilder.create().build();
    }

    // 动态分配数据源
    @Bean(name = "dataSource")
    public DynamicDataSource getDynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("master", getMasterSource());
        dataSourceMap.put("slave", getSlaveSource());
        //传入数据源map，AbstractRoutingDataSource将以key来分配数据源
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        //配置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(getMasterSource());
        return dynamicDataSource;
    }

    //mybatis配置
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(getDynamicDataSource());

        // 配置类型别名
        sessionFactoryBean.setTypeAliasesPackage("com.sh.entity");

        //配置mappel.xml映射路径
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:com/sh/mapper/*.xml"));

        return sessionFactoryBean;
    }


    /**
     * 自定义事务
     * MyBatis自动参与到spring事务管理中，无需额外配置，
     * 只要SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，
     * 否则事务管理会不起作用。
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManagers() {
        return new DataSourceTransactionManager(getDynamicDataSource());
    }

    // 主库事务管理器
    @Bean
    @Qualifier("masterTransaction")
    public PlatformTransactionManager masterTransaction(@Qualifier("master") DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }

    // 从库事务管理器
    @Bean
    @Qualifier("slaveTransaction")
    public PlatformTransactionManager slaveTransaction(@Qualifier("slave") DataSource slaveDataSource) {
        return new DataSourceTransactionManager(slaveDataSource);
    }

}
