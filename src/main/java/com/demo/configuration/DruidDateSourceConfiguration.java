package com.demo.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.boot.bind.PropertySourceUtils;
import org.springframework.boot.bind.RelaxedDataBinder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author xuezn
 * @Date 2018年12月12日 16:02:15
 */
@Configuration
public class DruidDateSourceConfiguration implements EnvironmentAware,BeanDefinitionRegistryPostProcessor{

    private DynamicDataSourceProperties dynamicDataSourceProperties;

    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {

        // 主数据源设置为默认注入的数据源
        dynamicDataSourceProperties.getMulti().keySet().forEach(
                e -> {
                    AnnotatedGenericBeanDefinition abd = BeanDefinitionRegistryUtil.decorateAbd(DruidDataSource.class);
                    abd.setDestroyMethodName("close");
                    if (e.equals(dynamicDataSourceProperties.getPrimary())){
                        abd.setPrimary(true);
                    }
                    abd.setPropertyValues(new MutablePropertyValues(objectToMap(dynamicDataSourceProperties.getMulti().get(e))));
                    BeanDefinitionRegistryUtil.doRegistBean(abd, e, beanDefinitionRegistry);
                }
        );
    }

    /**
     * 对象转map
     */
    private static Map<String, Object> objectToMap(Object obj) {
        if (obj == null) {
            return null;
        }
        //获取关联的所有类，本类以及所有父类
        Class oo = obj.getClass();
        List<Class> clazzs = new ArrayList<>();
        while (oo != null && oo != Object.class) {
            clazzs.add(oo);
            oo = oo.getSuperclass();
        }
        Map<String, Object> map = new HashMap<>(clazzs.size() * 8);
        try {
            for (Class clazz : clazzs) {
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    int mod = field.getModifiers();
                    //过滤 static 和 final 类型
                    if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                        continue;
                    }
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }
            }
        } catch (IllegalAccessException e) {
            // ignore
        }
        return map;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
        ConfigurableEnvironment env = (ConfigurableEnvironment) environment;
        Map<String, Object> map = PropertySourceUtils
                .getSubProperties(env.getPropertySources(), "spring.druid.");
        DynamicDataSourceProperties dynamicDataSourceProperties = new DynamicDataSourceProperties();
        RelaxedDataBinder relaxedDataBinder = new RelaxedDataBinder(dynamicDataSourceProperties,
                null);
        relaxedDataBinder.setConversionService(new DefaultConversionService());
        // 设置数据源属性
        relaxedDataBinder.bind(new MutablePropertyValues(map));
        this.dynamicDataSourceProperties = dynamicDataSourceProperties;
    }

}
