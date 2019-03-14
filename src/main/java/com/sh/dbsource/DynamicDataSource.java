package com.sh.dbsource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
/**
 * 继承spring  AbstractRoutingDataSource
 * @author Administrator
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static Logger LOGGER = LoggerFactory.getLogger(DynamicDataSource.class);

    //切换数据源
    @Override
    protected Object determineCurrentLookupKey() {

        LOGGER.info("=============切换数据源为:{}=============", DynamicDataSourceHolder.getDataSouce());
        //从本地线程副本中取出数据源name
        return DynamicDataSourceHolder.getDataSouce();
    }

}


