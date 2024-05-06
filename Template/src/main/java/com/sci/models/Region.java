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
@Table(name = "Regions")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor

public class Region implements Serializable {

    private static final long serialVersionUID = 6596828837219242458L;

    @Id
    @Column(name = "REGION_ID")
    private Integer regionId;
    @Column(name = "REGION_NAME")
    private String regionName;

}
