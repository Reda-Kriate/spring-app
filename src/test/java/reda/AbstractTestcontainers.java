package reda;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractTestcontainers {

    @BeforeAll
    static void beforeAll() {
        postgresqlcontainer.start();

        if(!postgresqlcontainer.isRunning()){
            throw new IllegalStateException("Docker not running !");
        }

            Flyway flyway = Flyway.configure().dataSource(
                    postgresqlcontainer.getJdbcUrl(),
                    postgresqlcontainer.getUsername(),
                    postgresqlcontainer.getPassword()).load();
            flyway.migrate();
            System.out.println();
    }

    @Container
    protected static final PostgreSQLContainer<?> postgresqlcontainer =
            new PostgreSQLContainer<>("postgres:latest")
                    .withDatabaseName("reda-dao-unit-test")
                    .withUsername("redakriate")
                    .withPassword("password");

    private static void registerDatasourceProperties(DynamicPropertyRegistry registry){
        registry.add(
                "spring.datasource.url",
                () -> postgresqlcontainer.getJdbcUrl()
        );
        registry.add(
                "spring.datasource.username",
                () -> postgresqlcontainer.getUsername()
        );
        registry.add(
                "spring.datasource.password",
                () -> postgresqlcontainer.getPassword()
        );
    }
    private static DataSource getDataSource(){
        return DataSourceBuilder.create()
                .driverClassName(postgresqlcontainer.getDriverClassName())
                .url(postgresqlcontainer.getJdbcUrl())
                .username(postgresqlcontainer.getUsername())
                .password(postgresqlcontainer.getPassword())
                .build();
    }
    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }
    protected static Faker FAKER = new Faker();
}
