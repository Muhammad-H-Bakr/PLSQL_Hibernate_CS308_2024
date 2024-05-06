package com.sci.models;

import lombok.*;

import java.io.Serializable;
import java.sql.Date;
//Composite key class:
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobHistoryCompositeKey implements Serializable {
    private static final long serialVersionUID = -7519983781399232210L;

    private Integer employeeId;
    private Date startDate;
}
