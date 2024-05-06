package com.sci.models;

import lombok.*;

import java.io.Serializable;

//Composite key class:
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PriceCompositeKey implements Serializable {
    private static final long serialVersionUID = 5L;

    private String roomNumber;
    private String season;
    private String weekday;

}
