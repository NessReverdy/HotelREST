package org.nessrev.hotelrest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating a new hotel")
public class HotelCreateRequest {

    @Schema(description = "Hotel name", example = "Hilton Grand")
    private String name;

    @Schema(description = "Hotel description", example = "A luxurious 5-star hotel in the city center.")
    private String description;

    @Schema(description = "Brand of the hotel", example = "Hilton")
    private String brand;

    @Schema(description = "Hotel address")
    private AddressDto address;

    @Schema(description = "Hotel contact information")
    private ContactsDto contacts;

    @Schema(description = "Check-in and check-out times")
    private ArrivalTimeDto arrivalTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Hotel address information")
    public static class AddressDto {

        @Schema(description = "House number", example = "42")
        private Integer houseNumber;

        @Schema(description = "Street name", example = "Main Street")
        private String street;

        @Schema(description = "City", example = "New York")
        private String city;

        @Schema(description = "Country", example = "USA")
        private String country;

        @Schema(description = "Postal code", example = "10001")
        private String postCode;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Hotel contact details")
    public static class ContactsDto {

        @Schema(description = "Phone number", example = "+1-800-555-1234")
        private String phoneNumber;

        @Schema(description = "Email address", example = "contact@hilton.com")
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Hotel arrival and departure times")
    public static class ArrivalTimeDto {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        @Schema(description = "Check-in time", example = "14:00")
        private LocalTime checkIn;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        @Schema(description = "Check-out time", example = "12:00")
        private LocalTime checkOut;
    }
}
