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
@Table(name = "A2_GUEST")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor

public class Guest implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @Column(name = "GUEST_ID")
    private String guestId;
    @Column(name = "FAMILY_NAME")
    private String familyName;
    @Column(name = "GIVEN_NAME")
    private String givenName;
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;
    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUEST", insertable = false, updatable = false)
    private List<Booking> bookings;
}
