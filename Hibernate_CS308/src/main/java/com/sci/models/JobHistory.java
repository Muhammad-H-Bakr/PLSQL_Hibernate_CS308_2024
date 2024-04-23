package com.sci.models;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "Job_History")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(JobHistoryCompositeKey.class) //Composite key.
public class JobHistory implements Serializable {

    private static final long serialVersionUID = 6572714351961244926L;
    @Id //Key part1.
    @Column(name = "EMPLOYEE_ID")
    private Integer employeeId;
    @Id //Key part2.
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
    @Column(name = "JOB_ID")
    private String jobId;
    @Column(name = "DEPARTMENT_ID")
    private Integer departmentId;

    //One employee used to work many times:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEE_ID", insertable = false, updatable = false)
    private Department employee;

    //Many previous employees used to work in one department:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID", insertable = false, updatable = false)
    private Department department;

    //Many previous employees used to have one job:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_ID", insertable = false, updatable = false)
    private Job job;
}