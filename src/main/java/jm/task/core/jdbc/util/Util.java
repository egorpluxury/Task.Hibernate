package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String dbURL="jdbc:mysql://localhost:3306/dbtasks";
    private static final String username="root";
    private static final String password="root";
    private static Connection connection=null;
    private static Util instance=null;
    private static SessionFactory sessionFactory=buildSessionFactory();


    protected static SessionFactory buildSessionFactory() {
        if (sessionFactory==null){
        try {
            Configuration configuration=new Configuration();
            Properties properties=new Properties();
            properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL,"jdbc:mysql://localhost:3306/dbtasks");
            properties.put(Environment.USER,"root");
            properties.put(Environment.PASS,"root");
            properties.put(Environment.DIALECT,"org.hibernate.dialect.MySQL8Dialect");
           // properties.put(Environment.SHOW_SQL,"true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
            properties.put(Environment.HBM2DDL_AUTO,"create-drop");
            configuration.setProperties(properties).addAnnotatedClass(User.class);
            ServiceRegistry serviceRegistry=new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory=configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        return sessionFactory;
    }
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    private Util() {

 try {
          if (connection==null || connection.isClosed()){
              connection=DriverManager.getConnection(dbURL, username, password);
           }
       } catch (SQLException e) {
           e.printStackTrace();
        }
    }
    public static Util getInstance(){
        if(instance==null){
            instance=new Util();
        }
        return instance;
 }
    public Connection getConnection() {
        return connection;
    }
}
