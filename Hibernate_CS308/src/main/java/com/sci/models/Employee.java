//Employees Sequenced, Self-Join, ManyToMany Relationship.
//JobHistory CompositeKey Solution.

package com.sci.models;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "employees")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor

//Sequencing:
@SequenceGenerator(name = "employees_gen", sequenceName = "employees_seq", allocationSize = 1)
public class Employee implements Serializable {

    private static final long serialVersionUID = -915428707036605461L;

    @Id //Sequence strategy:
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employees_gen")
    @Column(name = "employee_id")
    private Integer employeeId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "hire_date")
    private Date hireDate;
    @Column(name = "job_id")
    private String jobId;
    @Column(name = "salary")
    private Integer salary;
    @Column(name = "commission_pct")
    private Double commissionPct;
    @Column(name = "manager_id")
    private Integer managerId;
    @Column(name = "department_id")
    private Integer departmentId;


    //Each employee has a manager:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MANAGER_ID", insertable = false, updatable = false)
    private Employee manager;

    //Each employee (Manager) manages many employees:
    //This is a self-referencing relationship:
    @OneToMany(mappedBy = "manager")
    private List<Employee> managedEmployees;

    //Each Manager manages many departments:
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "DEPARTMENTS",
            joinColumns = {@JoinColumn(name = "MANAGER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DEPARTMENT_ID")}
    )
    private List<Department> managed_Departments;

    //One employee has many old jobs:
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID" /*FK*/, insertable = false, updatable = false)
    private List<JobHistory> previous_JobHistory;

    //Many employees have one job:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID", insertable = false, updatable = false)
    private Job job;
}