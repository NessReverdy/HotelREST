package org.nessrev.hotelrest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HotelShortInfo {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String phone;

    public HotelShortInfo(Long id, String name, String description, String address, String phone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
    }
}
