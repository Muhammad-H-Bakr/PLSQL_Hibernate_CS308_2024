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
@Table(name = "A2_ROOM_TYPE")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor

public class RoomType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ROOM_TYPE")
    private String roomTypeId;
    @Column(name = "SLEEPS")
    private Integer sleeps;
    @Column(name = "DESCRIPTION")
    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_TYPE", insertable = false, updatable = false)
    private List<Room> rooms;

}
