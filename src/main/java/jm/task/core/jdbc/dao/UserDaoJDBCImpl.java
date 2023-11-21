package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final Connection conn= Util.getInstance().getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement= conn.createStatement()){
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastName VARCHAR(100),age INT)");
        } catch (SQLException e) {
            System.err.println("Таблица с данным именем в базе данных уже существует"+e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement=conn.createStatement()){
            statement.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            System.err.println("Таблицы с данным именем в базе данных не существует"+e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement=conn.prepareStatement("INSERT INTO users (name,lastName,age) VALUES(?,?,?)");){
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при добавлении пользователя"+e);
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement=conn.prepareStatement("DELETE FROM users WHERE id=?")){
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя"+e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users=new ArrayList<>();
        try (ResultSet resultSet=conn.createStatement().executeQuery("SELECT * FROM users");){
            while (resultSet.next()){
                User user=new User(resultSet.getString("name"),resultSet.getString("lastName"),resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при выводе данных"+e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement=conn.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
