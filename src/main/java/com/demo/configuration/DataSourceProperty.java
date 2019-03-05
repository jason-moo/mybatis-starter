package com.demo.configuration;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Properties;

/**
 * @Author xuezn
 * @Date 2018年12月13日 14:47:25
 */
@Data
public class DataSourceProperty {

    /**
     * JDBC url 地址
     */
    private String url;
    /**
     * JDBC 用户名
     */
    private String username;
    /**
     * JDBC 密码
     */
    private String password;
    private String driverClassName = "com.mysql.jdbc.Driver";
    private int maxActive = 15;
    private int initialSize = 2;
    private long maxWait = 60000;
    private int minIdle = 1;
    private long timeBetweenEvictionRunsMillis = 3000;
    private long minEvictableIdleTimeMillis = 300000;
    private String validationQuery = "SELECT 'x'";
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private int maxPoolPreparedStatementPerConnectionSize = 20;
    private long timeBetweenLogStatsMillis = 300000;
    private boolean removeAbandoned = true;
    private int removeAbandonedTimeout = 180;
    private boolean logAbandoned = true;
    private boolean keepAlive = true;
    private String filters = "wall,stat";

}
