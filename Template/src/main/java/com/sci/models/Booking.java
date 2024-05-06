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
@Table(name = "A2_BOOKING")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor

public class Booking implements Serializable {
    private static final long serialVersionUID = 4L;

    @Id
    @Column(name = "BOOKING_NUMBER")
    private String bookingNumber;
    @Column(name = "GUEST")
    private String guest;
    @Column(name = "DATE_MADE")
    private Date dateMade;
    @Column(name = "REASON")
    private String reason;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKING_NUMBER", insertable = false, updatable = false)
    private List<Reservation> reservations;
}
