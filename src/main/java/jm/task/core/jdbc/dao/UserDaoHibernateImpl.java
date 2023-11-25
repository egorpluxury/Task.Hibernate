package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Transaction transaction;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
    try(Session session= Util.getSessionFactory().openSession()){
        transaction=session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users (id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR(100), lastName VARCHAR(100),age TINYINT)").executeUpdate();
        transaction.commit();
    }catch (Exception e){
        e.printStackTrace();
        transaction.rollback();
    }
    }

    @Override
    public void dropUsersTable() {
        try(Session session= Util.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session= Util.getSessionFactory().openSession();){
            transaction=session.beginTransaction();
            session.save(new User(name,lastName,age));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try(Session session= Util.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            session.delete(session.get(User.class,id));
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try(Session session=Util.getSessionFactory().openSession()){
            return session.createQuery("FROM User",User.class).getResultList();
        }catch (Exception e){
            throw e;
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session= Util.getSessionFactory().openSession()){
            transaction=session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
        }catch (HibernateException e){
            if (transaction!=null){
            transaction.rollback();}
        }
    }
}
