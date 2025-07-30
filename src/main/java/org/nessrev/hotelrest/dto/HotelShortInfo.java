package org.nessrev.hotelrest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Basic hotel information shown in listings and search results")
public class HotelShortInfo {

    @Schema(description = "Unique identifier of the hotel", example = "1")
    private Long id;

    @Schema(description = "Hotel name", example = "Hilton Grand")
    private String name;

    @Schema(description = "Short description of the hotel", example = "A modern hotel near the city center.")
    private String description;

    @Schema(description = "Formatted address as a single string", example = "123 Main St, New York, USA")
    private String address;

    @Schema(description = "Contact phone number", example = "+1-800-123-4567")
    private String phone;

    public HotelShortInfo(Long id, String name, String description, String address, String phone) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.address = address;
        this.phone = phone;
    }
}
