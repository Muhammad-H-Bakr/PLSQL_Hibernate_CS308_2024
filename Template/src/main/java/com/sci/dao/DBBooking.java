package com.sci.dao;


import com.sci.criteria.FilterQuery;
import com.sci.models.Booking;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "rawtypes"})
public class DBBooking {
    public List<Booking> getByFilter(List<FilterQuery> filterQueries) {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {
            // To be edited in other relations CRUD OPs:
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Booking> cr = cb.createQuery(Booking.class);
            Root<Booking> root = cr.from(Booking.class);

            Predicate[] predicates = new Predicate[filterQueries.size()];
            for (int i = 0; i < filterQueries.size(); i++) {
                switch (filterQueries.get(i).getOp()) {
                    case Equal:
                        predicates[i] =
                                cb.equal(root.get(filterQueries.get(i).
                                                getAttributeName()),
                                        filterQueries.get(i).getAttributeValue());
                        break;
                    case GreaterThan:
                        predicates[i] =
                                cb.greaterThan(root.get(filterQueries.
                                                get(i).getAttributeName()),
                                        (Comparable) filterQueries.get(i).
                                                getAttributeValue());
                        break;
                    case LessThan:
                        predicates[i] =
                                cb.lessThan(root.get(filterQueries.get(i).
                                                getAttributeName()),
                                        (Comparable) filterQueries.get(i).
                                                getAttributeValue());
                        break;
                    case NotEqual:
                        predicates[i] =
                                cb.notEqual(root.get(filterQueries.get(i).
                                                getAttributeName()),
                                        filterQueries.get(i).
                                                getAttributeValue());
                        break;
                    case IsNull:
                        predicates[i] =
                                cb.isNull(root.get(filterQueries.get(i).
                                        getAttributeName())
                                );
                        break;
                    case GreaterThanOrEqual:
                        predicates[i] =
                                cb.greaterThanOrEqualTo(
                                        root.get(filterQueries.get(i).
                                                getAttributeName()),
                                        (Comparable) filterQueries.get(i).
                                                getAttributeValue());
                        break;
                    case LessThanOrEqual:
                        predicates[i] =
                                cb.lessThanOrEqualTo(
                                        root.get(filterQueries.get(i).
                                                getAttributeName()),
                                        (Comparable) filterQueries.get(i).
                                                getAttributeValue());
                        break;
                    case Like:
                        /* <===> Where attribute like '%string%', can be customized. */
                        predicates[i] =
                                cb.like(root.get(filterQueries.get(i).
                                                getAttributeName()),
                                        "%" + (filterQueries.get(i).
                                                getAttributeValue()) + "%");
                        break;
                    case Between:
                        List<Comparable> values =
                                (List<Comparable>) filterQueries.get(i).
                                        getAttributeValue();
                        predicates[i] =
                                cb.between(root.get(filterQueries.get(i).
                                                getAttributeName()),
                                        values.get(0), values.get(1));
                        break;
                    case In:
                        List<Object> inQuery = (List<Object>)
                                filterQueries.get(i).getAttributeValue();
                        predicates[i] =
                                root.get(filterQueries.get(i).getAttributeName()).
                                        in(inQuery);
                        break;
                    default:
                        break;
                }
            }

            //This is their default, We need a more complex query
            cr.select(root).where(predicates);

            Query<Booking> query = session.createQuery(cr);
            return query.getResultList();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return new ArrayList<>();
    }

    public List<Booking> get() {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            //noinspection unchecked
            return session.createQuery("FROM Booking ").list();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public Booking read(String bookingNumber) {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            return session.get(Booking.class, bookingNumber);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }


    public String create(Booking booking) {

        Transaction transaction = null;
        String bookingNumber = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            bookingNumber = (String) session.save(booking);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }

        return bookingNumber;
    }

    public void update(Booking booking) {

        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            session.update(booking);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }
    }

    public void delete(String bookingNumber) {

        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            Booking booking = read(bookingNumber);

            session.delete(booking);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }
    }

}
