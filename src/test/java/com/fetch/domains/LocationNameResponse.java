package com.fetch.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationNameResponse {

    private String name;
    private Float lat;
    private Float lon;
    private String country;
    private String state;

}
