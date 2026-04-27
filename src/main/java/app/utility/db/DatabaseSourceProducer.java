package app.utility.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import javax.sql.DataSource;

@ApplicationScoped
public class DatabaseSourceProducer {

    @ApplicationScoped
    @Produces
    public DataSource createDataSource(){
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:mysql://localhost:3308/showroom");
        config.setUsername("root");
        config.setPassword("root123");

        return new HikariDataSource(config);
    }
}
