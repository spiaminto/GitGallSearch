package kr.granblue.gbfsearch.config;


import kr.granblue.gbfsearch.domain.dc.DcBoardEmbedding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "kr.granblue.gbfsearch.repository.postgre",
        entityManagerFactoryRef = "postgreEntityManager",
        transactionManagerRef = "postgreTransactionManager"
)
public class PostgreDataSourceConfig {

    @Value("${spring.profiles.active}")
    private String profile;
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.postgre")
    public DataSourceProperties postgreDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource postgreDataSource() {
        return postgreDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(name = "postgreEntityManager")
    public LocalContainerEntityManagerFactoryBean postgreEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {
//        String defaultSchema = profile.equals("prod") ? "scrape_prod" : "scrape_test";
//        System.out.println("defaultSchema: " + defaultSchema);
//
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        properties.put("hibernate.default_schema", defaultSchema);

        return builder.dataSource(postgreDataSource()).packages(DcBoardEmbedding.class)
                .properties(properties)
                .build();
    }

    @Bean(name = "postgreTransactionManager")
    public PlatformTransactionManager postgreTrnasactionMAnager(
            @Qualifier("postgreEntityManager") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
//        String defaultSchema = profile.equals("prod") ? "scrape_prod" : "scrape_test";
//        properties.setProperty("hibernate.default_schema", defaultSchema);
//        entityManagerFactoryBean.setJpaProperties(properties);
        return new JpaTransactionManager(entityManagerFactoryBean.getObject());
    }


}
