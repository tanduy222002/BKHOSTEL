package com.BKHOSTEL.BKHOSTEL.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String streetAddress;
    private String ward;
    private String district;
    private String city;
}
