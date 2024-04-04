package com.sci.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobHistoryCompositeKey implements Serializable {
    private static final long serialVersionUID = -7519983781399232210L;

    private Integer employeeId;
    private Date startDate;
}
