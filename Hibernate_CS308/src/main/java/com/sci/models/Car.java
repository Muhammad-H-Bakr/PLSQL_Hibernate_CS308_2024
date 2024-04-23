package com.sci.models;
import java.io.Serializable;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "CAR")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
public class Car implements  Serializable{
    private static final long serialVersionUID = 2722273942263295807L;

    @Id
    @Column(name = "ID")
    private Integer id;
    @Column(name = "BRAND")
    private String name;
    @Column(name = "PERSON_ID")
    private Integer personId;

    //Many cars are owned by one person:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Person owner;
}
