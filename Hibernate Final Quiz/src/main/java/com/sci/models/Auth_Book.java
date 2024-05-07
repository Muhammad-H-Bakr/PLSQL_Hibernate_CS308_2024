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
@Table(name = "Auth_book")
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@AllArgsConstructor
@IdClass(Auth_BookCompositeKey.class)

public class Auth_Book implements Serializable {

    private static final long serialVersionUID = 21L;

    @Id
    @Column(name = "auth_id")
    private Integer authId;
    @Id
    @Column(name = "book_id")
    private Integer bookId;
}
