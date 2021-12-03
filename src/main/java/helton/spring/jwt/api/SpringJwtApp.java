package helton.spring.jwt.api;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@SpringBootApplication
@EnableSwagger2
public class SpringJwtApp
{
	public static void main(String... args)
	{
		SpringApplication.run(SpringJwtApp.class, args);
	}
	
	@Bean
	public DataSource dataSourceFactory()
	{
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUsername("test");
		dataSource.setPassword("test");
		dataSource.setUrl("jdbc:mysql://localhost:3306/MOVIES_APP?createDatabaseIfNotExist=true");

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
	{
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

		em.setDataSource(dataSourceFactory());

		JpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(adapter);
		em.setPackagesToScan(new String[] { "helton.spring.jwt.api" });

		return em;
	}

}
