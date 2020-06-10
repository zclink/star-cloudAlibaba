package com.gupaoedu.sca.dao.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan( basePackages = {"com.gupaoedu.sca.dao" },
        sqlSessionFactoryRef = "demoSqlSessionFactoryBean" )
public class DemoDataSourceConfig {

    @Resource
    private DataSource dataSource;

    @Bean( name = "demoSqlSessionFactoryBean" )
    @ConfigurationProperties( prefix = "mybatis.demo" )
    public SqlSessionFactory smsSqlSessionFactoryBean () throws Exception {

        try {
            SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
            sessionFactory.setDataSource (dataSource);
            return sessionFactory.getObject ();
        } catch ( Exception e ) {
            e.printStackTrace();
            return null;
        }
    }


}
