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
@Table(name = "A2_ROOM")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor

public class Room implements Serializable {
    private static final long serialVersionUID = 2L;

    @Id
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Column(name = "ROOM_TYPE")
    private String roomTypeId;
    @Column(name = "SHOWER")
    private String shower;
    @Column(name = "BATH")
    private String bath;
    @Column(name = "BALCONY")
    private String balcony;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_NUMBER", insertable = false, updatable = false)
    private List<Price> prices;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_NUMBER", insertable = false, updatable = false)
    private List<Reservation> reservations;
}
