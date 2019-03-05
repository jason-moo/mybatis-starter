package com.demo.configuration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;

/**
 * @Author xuezn
 * @Date 2018年12月12日 15:56:53
 */
public class DruidDataSourceBuilder extends DataSourceBuilder{

    public DruidDataSourceBuilder(ClassLoader classLoader) {
        super(classLoader);
    }

}
