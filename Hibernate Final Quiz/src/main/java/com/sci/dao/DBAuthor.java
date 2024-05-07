package com.sci.dao;
import com.sci.models.Author;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

@SuppressWarnings({"unchecked"})
public class DBAuthor {
    public List<Author> get() {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            //noinspection unchecked
            return session.createQuery("FROM Author ").list();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public Author read(Integer authId) {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            return session.get(Author.class, authId);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }


    public Integer create(Author author) {

        Transaction transaction = null;
        Integer authId = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            authId = (Integer) session.save(author);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }

        return authId;
    }

}
