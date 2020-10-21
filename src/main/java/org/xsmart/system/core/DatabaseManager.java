package org.xsmart.system.core;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.xsmart.system.util.PropsUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class DatabaseManager {

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<Connection>();
    private static final QueryRunner QUERYRUNNER = new QueryRunner();
    private static final BasicDataSource basicDataSource;


    static {
        Properties properties = PropsUtil.loadPropFile("config.properties");
        String DRIVER = PropsUtil.getString(properties,"jdbc.driver");
        String USERNAME = PropsUtil.getString(properties,"jdbc.username");
        String PASSWORD = PropsUtil.getString(properties,"jdbc.password");
        String URL = PropsUtil.getString(properties,"jdbc.url");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            //todo 抛异常、日志
            e.printStackTrace();
        }

        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(DRIVER);
        basicDataSource.setUrl(URL);
        basicDataSource.setUsername(USERNAME);
        basicDataSource.setPassword(PASSWORD);

    }

    public static Connection getConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection == null){
            try {
                connection = basicDataSource.getConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }finally {
                CONNECTION_HOLDER.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection(){
        Connection connection = CONNECTION_HOLDER.get();
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException throwables) {
                //todo 日志
                throwables.printStackTrace();
            } finally {
                CONNECTION_HOLDER.remove();
            }

        }
    }

    public static <T> List<T> queruEntityList(String sql,Class<T> entityClass,Object... params){
        List<T> entityList = null;
        Connection connection = getConnection();
        try {
            entityList = QUERYRUNNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException throwables) {
            //todo 日志
            throwables.printStackTrace();
        }
        return entityList;
    }

}
