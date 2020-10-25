package org.xsmart.system.core;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.log4j.Logger;
import org.xsmart.aspect.ControllerAspect;
import org.xsmart.system.util.PropsUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DatabaseManager {

    private static Logger logger = Logger.getLogger(DatabaseManager.class);
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
            logger.error("DatabaseManager init error :" + e.getMessage());
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
            } catch (SQLException e) {
                logger.error("DatabaseManager closeConnection error :" + e.getMessage());
                e.printStackTrace();
            } finally {
                CONNECTION_HOLDER.remove();
            }

        }
    }

    public static <T> List<T> queryEntityList(String sql, Class<T> entityClass, Object... params){
        List<T> entityList = null;
        Connection connection = getConnection();
        try {
            entityList = QUERYRUNNER.query(connection,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException throwables) {
            logger.error("DatabaseManager queryEntityList error :" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return entityList;
    }

    public static <T> T queryEntity(String sql, Class<T> entityClass, Object... params){
        T entity = null;
        Connection connection = getConnection();
        try {
            entity = QUERYRUNNER.query(connection,sql,new BeanHandler<T>(entityClass),params);
        } catch (SQLException throwables) {
            logger.error("DatabaseManager queryEntity error :" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return entity;
    }

    public static List<Map<String,Object>> execQuery(String sql, Object... params){
        List<Map<String,Object>> result = null;
        Connection connection = getConnection();
        try {
            result = QUERYRUNNER.query(connection,sql,new MapListHandler(),params);
        } catch (SQLException throwables) {
            logger.error("DatabaseManager execQuery error :" + throwables.getMessage());
            throwables.printStackTrace();
        }
        return result;
    }

    public static int update(String sql, Object... params){
        int affectedRow = 0;
        Connection connection = getConnection();
        try {
            affectedRow = QUERYRUNNER.update(connection,sql,params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return affectedRow;
    }

    public static int execUpdate(String sql,Object... params){
        Connection connection = getConnection();
        int row = 0;
        try {
            row = QUERYRUNNER.update(connection,sql, params);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return row;
    }

    public static <T> boolean insertEntity(Class<T> clz,Map<String,Object> fieldValueMap){

        StringBuilder sql = new StringBuilder();
        StringBuilder insertField = new StringBuilder(" (");
        StringBuilder insertValue = new StringBuilder(" (");
        sql.append("insert into ").append(getTableName(clz));
        for(String fieldName : fieldValueMap.keySet()){
            insertField.append("`").append(fieldName).append("`").append(",");
            insertValue.append("?,");
        }
        insertField.delete(insertField.length() - 1,insertField.length()).append(")");
        insertValue.delete(insertValue.length() - 1,insertValue.length()).append(")");
        sql.append(insertField.toString()).append(" values").append(insertValue.toString());

        Object[] params = fieldValueMap.values().toArray();
        return execUpdate(sql.toString(),params) == 1;
    }

    private static String getTableName(Class<?> clz){
        return clz.getSimpleName().toLowerCase();
    }


}
