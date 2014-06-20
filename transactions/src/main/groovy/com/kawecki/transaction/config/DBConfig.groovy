package com.kawecki.transaction.config

import com.mchange.v2.c3p0.ComboPooledDataSource
import org.hibernate.SessionFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.hibernate4.HibernateTransactionManager
import org.springframework.orm.hibernate4.LocalSessionFactoryBean
import org.springframework.transaction.PlatformTransactionManager

import javax.sql.DataSource
import java.beans.PropertyVetoException

@Configuration
class DBConfig {

    @Bean
    DataSource dataSource(
            @Value('${db.driver}') String driver,
            @Value('${db.url}') String url,
            @Value('${db.user}') String user,
            @Value('${db.password}') String password,
            @Value('${db.maxpool: 50}') int maxPoolSize,
            @Value('${db.maxIdleTime: 30}') int maxIdleTime,
            @Value('${db.statements: 50}') int maxStatements,
            @Value('${db.testsql: SELECT 1}') String testQuery,
            @Value('${db.test: true}') boolean testConnectionOnCheckout) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource()
        dataSource.setCheckoutTimeout(10000)
        dataSource.setAutoCommitOnClose(false)
        dataSource.setInitialPoolSize(1)
        dataSource.setMinPoolSize(1)
        dataSource.setMaxPoolSize(maxPoolSize)
        dataSource.setMaxIdleTime(maxIdleTime)
        dataSource.setMaxStatements(maxStatements)
        dataSource.setDriverClass(driver)
        dataSource.setJdbcUrl(url)
        dataSource.setUser(user)
        dataSource.setPassword(password)
        dataSource.setPreferredTestQuery(testQuery)
        dataSource.setTestConnectionOnCheckout(testConnectionOnCheckout)
        return dataSource
    }

    @Bean
    Properties hibernateProperties(
            @Value('${hibernate.dialect:org.hibernate.dialect.PostgreSQLDialect}') String dialect,
            @Value('${hibernate.hbm2ddl.auto:create}') String hbm2ddl,
            @Value('${hibernate.show_sql:true}') boolean showSql,
            @Value('${db.fetch: 0}') int fetchSize,
            @Value('${hibernate.format_sql: true}') boolean formatSql) {
        Properties properties = new Properties()
        properties.put("hibernate.dialect", dialect)
        properties.put("hibernate.show_sql", showSql)
        properties.put("hibernate.format_sql", formatSql)
        properties.put("hibernate.hbm2ddl.auto", hbm2ddl)
        properties.put("hibernate.jdbc.fetch_size", fetchSize)
        properties.put("hibernate.order_updates", true)
        properties.put("hibernate.cache.use_second_level_cache", false)
        properties.put("hibernate.cache.use_query_cache", false)
        properties.put("org.hibernate.envers.audit_table_prefix", "AUDIT_")
        properties.put("org.hibernate.envers.audit_table_suffix", "")
        return properties
    }

    @Bean
    SessionFactory sessionFactory(DataSource dataSource, @Qualifier("hibernateProperties") Properties properties) throws Exception {
        LocalSessionFactoryBean  sessionFactoryBean = new LocalSessionFactoryBean()
        sessionFactoryBean.setDataSource(dataSource)
        sessionFactoryBean.setPackagesToScan('com.kawecki.transaction')
        sessionFactoryBean.setHibernateProperties(properties)
        sessionFactoryBean.afterPropertiesSet()
        return sessionFactoryBean.getObject()
    }

    @Bean
    PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory)
    }
}
