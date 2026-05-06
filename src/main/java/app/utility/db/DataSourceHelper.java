package app.utility.db;

//import app.utility.bootstrap.InitBootstrap;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//@InitBootstrap
@ApplicationScoped
public class DataSourceHelper {

    private static volatile HikariDataSource dataSource;

    @Inject
    @Named("dbParamHost")
    private String dbParamHost;

    @Inject
    @Named("dbParamPort")
    private int dbParamPort;

    @Inject
    @Named("dbParamName")
    private String dbParamName;

    @Inject
    @Named("dbParamUser")
    private String dbParamUser;

    @Inject
    @Named("dbParamPwd")
    private String dbParamPwd;

    public DataSource createDataSource() {
        if (dataSource == null) {
            synchronized (DataSourceHelper.class) {
                if (dataSource == null) {
                    HikariConfig config = new HikariConfig();

                    config.setJdbcUrl(
                            "jdbc:mysql://" + dbParamHost + ":" + dbParamPort + "/" + dbParamName +
                                    "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
                    );

                    config.setUsername(dbParamUser);
                    config.setPassword(dbParamPwd);

                    config.setMaximumPoolSize(10);
                    config.setMinimumIdle(2);
                    config.setIdleTimeout(30000);

                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }

//    public Connection getConnection() throws SQLException {
//        return this.getDataSource().getConnection();
//    }
//
//    public String getBaseUrlWithoutDB() {
//        return "jdbc:mysql://" + dbParamHost + ":" + dbParamPort +
//                "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
//    }
//
//    public String getDbName() {
//        return dbParamName;
//    }
//
//    public String getUser() {
//        return dbParamUser;
//    }
//
//    public String getPassword() {
//        return dbParamPwd;
//    }
}