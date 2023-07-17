package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
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
                session.createSQLQuery("""
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
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#hql-insert (only from other table)
                // https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#pc-persist
                session.persist(new User(name, lastName, age));
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.createQuery("DELETE FROM User WHERE id=:id").setParameter("id", id)
                        .executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#hql-select
                List<User> users = session.createQuery("FROM User", User.class).list();
                transaction.commit();
                return users;
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                // https://docs.jboss.org/hibernate/orm/5.6/userguide/html_single/Hibernate_User_Guide.html#hql-delete
                session.createQuery("DELETE FROM User").executeUpdate();
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                e.printStackTrace();
            }
        } catch (PersistenceException e) {
            e.printStackTrace();
        }
    }
}
