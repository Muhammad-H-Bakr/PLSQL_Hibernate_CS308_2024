package com.sci.dao;

import com.sci.criteria.FilterQuery;
import com.sci.models.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@SuppressWarnings("ALL")
public class DBManagement {
    public static class DBCar {

        public List<Car> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Car ").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Car read(Integer Id) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Car.class, Id);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public Integer create(Car car) {

            Transaction transaction = null;
            int Id = 0;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Id = (Integer) session.save(car);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return Id;
        }

        public void update(Car car) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(car);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(Integer Id) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Car car = read(Id);

                session.delete(car);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }

    public static class DBPerson {

        public List<Person> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Person ").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Person read(Integer Id) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Person.class, Id);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public Integer create(Person person) {

            Transaction transaction = null;
            int Id = 0;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Id = (Integer) session.save(person);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return Id;
        }

        public void update(Person person) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(person);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(Integer Id) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Person person = read(Id);

                session.delete(person);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }

    public static class DBEmployee {
        public List<Employee> getByFilter(List<FilterQuery> filterQueries) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {
                // To be edited in other relations CRUD OPs:
                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaQuery<Employee> cr = cb.createQuery(Employee.class);
                Root<Employee> root = cr.from(Employee.class);

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
                                            (Comparable) filterQueries.get(i).
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
                            predicates[i] =
                                    cb.like(root.get(filterQueries.get(i).
                                                    getAttributeName()),
                                            filterQueries.get(i).
                                                    getAttributeValue().toString());
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
//                // Adding DISTINCT:
//                cr.distinct(true);
//
//                //Sorting by something: [use desc instead of asc for order if needed]:
//                cr.orderBy(cb.asc(root.get("hireDate")));
//
//                //By Default takes thier conjuction, this is union:
//                Predicate pr = cb.or(predicates);
//                cr.select(root).where(pr);
                cr.select(root).where(predicates);
                Query<Employee> query = session.createQuery(cr);
                return query.getResultList();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return new ArrayList<>();
        }

        public List<Employee> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Employee").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Employee read(Integer employeeId) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Employee.class, employeeId);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public Integer create(Employee employee) {

            Transaction transaction = null;
            int employeeId = 0;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                employeeId = (Integer) session.save(employee);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return employeeId;
        }

        public void update(Employee employee) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(employee);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(Integer employeeId) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Employee employee = read(employeeId);

                session.delete(employee);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }

    public static class DBRegion {

        public List<Region> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Region").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Region read(Integer regionId) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Region.class, regionId);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public Integer create(Region region) {

            Transaction transaction = null;
            int regionId = 0;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                regionId = (Integer) session.save(region);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return regionId;
        }

        public void update(Region region) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(region);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(Integer regionId) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Region region = read(regionId);

                session.delete(region);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }

    public static class DBJobHistory {

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

    public static class DBLocation {

        public List<Location> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Location").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Location read(Integer locationId) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Location.class, locationId);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public Integer create(Location location) {

            Transaction transaction = null;
            int locationId = 0;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                locationId = (Integer) session.save(location);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return locationId;
        }

        public void update(Location location) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(location);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(Integer locationId) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Location location = read(locationId);

                session.delete(location);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }

    public static class DBJob {

        public List<Job> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Job ").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Job read(String jobId) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Job.class, jobId);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public String create(Job job) {

            Transaction transaction = null;
            String jobId = "";

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                jobId = (String) session.save(job);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return jobId;
        }

        public void update(Job job) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(job);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(String jobId) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Job job = read(jobId);

                session.delete(job);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }

    public static class DBCountry {

        public List<Country> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Country").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Country read(String countryId) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Country.class, countryId);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public String create(Country country) {

            Transaction transaction = null;
            String countryId = "";

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                countryId = (String) session.save(country);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return countryId;
        }

        public void update(Country country) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(country);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(String countryId) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Country country = read(countryId);

                session.delete(country);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }

    public static class DBDepartment {

        public List<Department> get() {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                //noinspection unchecked
                return session.createQuery("FROM Department").list();

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }

        public Department read(Integer departmentId) {

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                return session.get(Department.class, departmentId);

            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            }

            return null;
        }


        public Integer create(Department department) {

            Transaction transaction = null;
            int departmentId = 0;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                departmentId = (Integer) session.save(department);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }

            return departmentId;
        }

        public void update(Department department) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                session.update(department);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

        public void delete(Integer departmentId) {

            Transaction transaction = null;

            try (Session session = DBConfig.SESSION_FACTORY.openSession()) {

                transaction = session.beginTransaction();

                Department department = read(departmentId);

                session.delete(department);

                transaction.commit();

            } catch (Exception ex) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.err.println(ex.getMessage());
            }
        }

    }
}
