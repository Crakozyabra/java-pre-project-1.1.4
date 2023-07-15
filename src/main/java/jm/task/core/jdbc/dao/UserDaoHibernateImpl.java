package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createNativeQuery("""
                            CREATE TABLE IF NOT EXISTS users(
                                id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                name VARCHAR(128),
                                last_name VARCHAR(128),
                                age TINYINT UNSIGNED
                            );
                        """).executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createNativeQuery("DROP TABLE IF EXISTS users").executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#sql-query-parameters
                session.createNativeQuery("INSERT INTO users(name, last_name, age) VALUES (:name, :lastName, :age);")
                        .setParameter("name", name)
                        .setParameter("lastName", lastName)
                        .setParameter("age", age)
                        .executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createNativeQuery("DELETE FROM users WHERE id=:id").setParameter("id", id).executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#sql-entity-query
                List<User> users = session.createNativeQuery("SELECT * FROM users", User.class).list();
                transaction.commit();
                return users;
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createNativeQuery("DELETE FROM users").executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
