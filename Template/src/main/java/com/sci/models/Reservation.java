package com.sci.models;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "A2_RESERVATION")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ReservationCompositeKey.class) //Composite key.

public class Reservation implements Serializable {
    private static final long serialVersionUID = 8L;

    @Id
    @Column(name = "BOOKING_NUMBER")
    private String bookingNumber;
    @Id
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Id
    @Column(name = "DATE_RESERVED")
    private Date dateReserved;

}