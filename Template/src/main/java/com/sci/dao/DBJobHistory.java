package com.sci.dao;

import com.sci.models.JobHistory;
import com.sci.models.JobHistoryCompositeKey;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Date;
import java.util.List;

public  class DBJobHistory {

    public List<JobHistory> get() {

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            //noinspection unchecked
            return session.createQuery("FROM JobHistory").list();

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }

    public JobHistory read(Integer employeeId, Date startDate) {
        JobHistoryCompositeKey key = new JobHistoryCompositeKey(employeeId, startDate);

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            return session.get(JobHistory.class, key);

        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        return null;
    }


    public JobHistoryCompositeKey create(JobHistory jobHistory) {

        Transaction transaction = null;
        JobHistoryCompositeKey key = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            key = (JobHistoryCompositeKey) session.save(jobHistory);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }

        return key;
    }

    public void update(JobHistory jobHistory) {

        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            session.update(jobHistory);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }
    }

    public void delete(Integer employeeId, Date date) {
        JobHistoryCompositeKey key = new JobHistoryCompositeKey(employeeId, date);
        Transaction transaction = null;

        try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

            transaction = session.beginTransaction();

            JobHistory jobHistory = read(employeeId, date);

            session.delete(jobHistory);

            transaction.commit();

        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println(ex.getMessage());
        }
    }

}