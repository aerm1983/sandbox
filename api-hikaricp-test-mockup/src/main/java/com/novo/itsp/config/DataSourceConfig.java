package com.novo.itsp.config;

import java.util.Properties;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import com.novo.itsp.utils.Utilities;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
	
	@Value("${client.datasource}")
	private String clientDatasource;
	
	private static final Logger log =  LoggerFactory.getLogger(DataSourceConfig.class);
	
	
    @Bean
    public DataSource getDataSource() {
    	
    	Utilities utilities = new Utilities();
    	
    	Properties p = utilities.getConfig(clientDatasource);
    	
        HikariConfig hikariConfig = new HikariConfig();
        
        String p_jndi = p.getProperty("api.app.datasource.hikari.jndi");
        String p_url = p.getProperty("api.app.datasource.url");
        String p_username = p.getProperty("api.app.datasource.username");
        String p_password = p.getProperty("api.app.datasource.password"); 
        
        if ( p_jndi == null & p_url != null & p_username != null & p_password != null ) {
            hikariConfig.setJdbcUrl(p_url);
            hikariConfig.setUsername(p_username);
            hikariConfig.setPassword(p_password);
        } else if ( p_jndi != null )  {
        	// hikariConfig.setDataSourceJNDI(p_jndi);
        	JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
            dsLookup.setResourceRef(true);
            DataSource ds = dsLookup.getDataSource(p.getProperty("api.app.datasource.hikari.jndi"));
            hikariConfig.setDataSource( ds );
            log.info("Conectandose con el jndi: " + p.getProperty("api.app.datasource.hikari.jndi"));
        	
        } else {
        	log.error("No es posible definir DataSource/HikariCP");
        }
        
        hikariConfig.setPoolName(p.getProperty("api.app.datasource.hikari.pool-name"));
        hikariConfig.setConnectionTestQuery(p.getProperty("api.app.datasource.hikari.connection-test-query"));
        hikariConfig.setConnectionInitSql( p.getProperty("api.app.datasource.hikari.connection-init-sql") );
        
        hikariConfig.setMaximumPoolSize( Integer.parseInt( p.getProperty("api.app.datasource.hikari.maximum-pool-size") ) );
        hikariConfig.setMinimumIdle( Integer.parseInt( p.getProperty("api.app.datasource.hikari.set-minimum-idle") ) );
        
        hikariConfig.setMaxLifetime( Integer.parseInt( p.getProperty("api.app.datasource.hikari.max-lifetime") ) );
        hikariConfig.setIdleTimeout( Integer.parseInt( p.getProperty("api.app.datasource.hikari.idle-timeout") ) ); // 30000
        
        hikariConfig.setConnectionTimeout( Integer.parseInt( p.getProperty("api.app.datasource.hikari.connection-timeout") ) );
        hikariConfig.setValidationTimeout( Integer.parseInt( p.getProperty("api.app.datasource.hikari.validation-timeout") ) ); // 10000
        
        log.info("................ HIKARI_DATASOURCE_CONFIG - INICIO ................");
        log.info("PoolName: " + hikariConfig.getPoolName());
        log.info("DataSourceJNDI: " + hikariConfig.getDataSourceJNDI());
        
        log.info("ConnectionTestQuery: " + hikariConfig.getConnectionTestQuery());
        log.info("ConnectionInitSql: " + hikariConfig.getConnectionInitSql());
        
        log.info("MaximumPoolSize: " + hikariConfig.getMaximumPoolSize());
        log.info("MinimumIdle: " + hikariConfig.getMinimumIdle());
        
        log.info("MaxLifetime: " + hikariConfig.getMaxLifetime());
        log.info("IdleTimeout: " + hikariConfig.getIdleTimeout());
        
        log.info("ConnectionTimeout: " + hikariConfig.getConnectionTimeout());
        log.info("ValidationTimeout: " + hikariConfig.getValidationTimeout());
        log.info("................ HIKARI_DATASOURCE_CONFIG - FIN ................");
        
        return new HikariDataSource(hikariConfig);
    }
 
    @Bean
    public NamedParameterJdbcTemplate JdbcTemplateConfig() {
        return new NamedParameterJdbcTemplate(getDataSource());
    }
	
}