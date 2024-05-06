package com.sci.models;

import java.io.Serializable;
import javax.persistence.*;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "A2_PRICE_LIST")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PriceCompositeKey.class) //Composite key.
public class Price implements Serializable {
    private static final long serialVersionUID = 6L;

    @Id
    @Column(name = "ROOM_NUMBER")
    private String roomNumber;
    @Id
    @Column(name = "SEASON")
    private String season;
    @Id
    @Column(name = "WEEKDAY")
    private String weekday;
    @Column(name = "PRICE")
    private Integer price;

}