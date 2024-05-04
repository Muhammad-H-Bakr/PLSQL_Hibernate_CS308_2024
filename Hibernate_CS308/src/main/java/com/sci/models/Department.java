package com.sci.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "departments")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "departments_gen", sequenceName = "departments_seq",
        allocationSize = 1)
public class Department implements Serializable {

    private static final long serialVersionUID = 4396093107304957078L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_gen")
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;
    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @Column(name = "LOCATION_ID")
    private Integer locationId;

    //One department has many employees:
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EMPLOYEES",
            joinColumns = {@JoinColumn(name = "DEPARTMENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "EMPLOYEE_ID")}
    )
    private List<Employee> employees;

    //Many departments are located in one location:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LOCATION_ID", insertable = false, updatable = false)
    private Location location;

    //One department has many previous employees:
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", insertable = false, updatable = false)
    private List<JobHistory> previous_Department_Employees;
}
