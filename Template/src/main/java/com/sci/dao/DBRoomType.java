package com.sci.dao;


import com.sci.criteria.FilterQuery;
import com.sci.models.RoomType;
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
public class DBRoomType {
    public List<RoomType> getByFilter(List<FilterQuery> filterQueries) {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {
            // To be edited in other relations CRUD OPs:
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<RoomType> cr = cb.createQuery(RoomType.class);
            Root<RoomType> root = cr.from(RoomType.class);

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

            Query<RoomType> query = session.createQuery(cr);
            return query.getResultList();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return new ArrayList<>();
    }

    public List<RoomType> get() {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            //noinspection unchecked
            return session.createQuery("FROM RoomType ").list();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public RoomType read(String roomTypeId) {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            return session.get(RoomType.class, roomTypeId);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }


    public String create(RoomType roomType) {

        Transaction transaction = null;
        String roomTypeId = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            roomTypeId = (String) session.save(roomType);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }

        return roomTypeId;
    }

    public void update(RoomType roomType) {

        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            session.update(roomType);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }
    }

    public void delete(String roomTypeId) {

        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            RoomType roomType = read(roomTypeId);

            session.delete(roomType);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }
    }

}
