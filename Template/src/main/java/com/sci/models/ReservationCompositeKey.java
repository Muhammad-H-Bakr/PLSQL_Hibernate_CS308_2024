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
public class ReservationCompositeKey implements Serializable {
    private static final long serialVersionUID = 7L;

    private String bookingNumber;
    private String roomNumber;
    private Date dateReserved;

}
