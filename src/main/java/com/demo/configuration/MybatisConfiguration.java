package com.demo.configuration;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author xuezn
 * @Date 2018年12月13日 15:53:01
 */
@Configuration
public class MybatisConfiguration {

    @Bean(name = "masterSqlSessionFactoryBean")
    public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean(@Qualifier("master") DataSource dataSource) throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath:mapper/db1/*.xml"));
        mybatisSqlSessionFactoryBean.setConfigLocation(pathMatchingResourcePatternResolver.getResource("classpath:config/mybatis-config.xml"));
        return mybatisSqlSessionFactoryBean;
    }

    @Bean(name = "mapperScannerConfigurer")
    public static MapperScannerConfigurer mybatisSqlSessionFactoryBean(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("masterSqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.demo.mapper.db1");
        return mapperScannerConfigurer;
    }

    @Bean(name = "otherSqlSessionFactoryBean")
    public MybatisSqlSessionFactoryBean otherMybatisSqlSessionFactoryBean(@Qualifier("other") DataSource dataSource) throws IOException{
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setVfs(SpringBootVFS.class);
        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setMapperLocations(pathMatchingResourcePatternResolver.getResources("classpath:mapper/db2/*.xml"));
        mybatisSqlSessionFactoryBean.setConfigLocation(pathMatchingResourcePatternResolver.getResource("classpath:config/mybatis-config.xml"));
        return mybatisSqlSessionFactoryBean;
    }

    @Bean(name = "otherMapperScannerConfigurer")
    public static MapperScannerConfigurer otherMybatisSqlSessionFactoryBean(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("otherSqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage("com.demo.mapper.db2");
        return mapperScannerConfigurer;
    }
}
