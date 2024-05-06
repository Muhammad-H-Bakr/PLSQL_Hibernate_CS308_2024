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

}