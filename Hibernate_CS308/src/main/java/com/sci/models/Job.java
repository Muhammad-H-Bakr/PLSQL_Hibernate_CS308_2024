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
@Table(name = "Jobs")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor

public class Job implements Serializable {

    private static final long serialVersionUID = 2993868379008216372L;

    @Id
    @Column(name = "JOB_ID")
    private String jobId;
    @Column(name = "JOB_TITLE")
    private String jobTitle;
    @Column(name = "MIN_SALARY")
    private Integer minSalary;
    @Column(name = "MAX_SALARY")
    private Integer maxSalary;

    //One Job has many employees doing it:
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID", insertable = false, updatable = false)
    private List<Employee> employees;

    //One Job had many employees doing it:
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID", insertable = false, updatable = false)
    private List<JobHistory> employees_JobHistory;
}