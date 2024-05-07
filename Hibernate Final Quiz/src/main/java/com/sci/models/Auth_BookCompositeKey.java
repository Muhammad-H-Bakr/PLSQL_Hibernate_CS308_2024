package com.sci.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auth_BookCompositeKey implements Serializable {
    private static final long serialVersionUID = 1000L;

    private Integer authId;
    private Integer bookId;
}
