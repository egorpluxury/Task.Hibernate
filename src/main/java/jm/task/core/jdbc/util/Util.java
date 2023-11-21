package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String dbURL="jdbc:mysql://localhost:3306/dbtasks";
    private static final String username="root";
    private static final String password="root";
    private static Connection connection=null;
    private static Util instance=null;

    public Util() {
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
