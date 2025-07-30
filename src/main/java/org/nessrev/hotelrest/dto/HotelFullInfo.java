package org.nessrev.hotelrest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Full information about a hotel")
public class HotelFullInfo {

    @Schema(description = "Unique identifier of the hotel", example = "1")
    private Long id;

    @Schema(description = "Hotel name", example = "Hilton Grand")
    private String name;

    @Schema(description = "Hotel description", example = "A luxurious 5-star hotel in the city center.")
    private String description;

    @Schema(description = "Brand name of the hotel", example = "Hilton")
    private String brand;

    @Schema(description = "Hotel address")
    private AddressDto address;

    @Schema(description = "Contact information")
    private ContactsDto contacts;

    @Schema(description = "Check-in and check-out times")
    private ArrivalTimeDto arrivalTime;

    @Schema(description = "List of amenities available at the hotel", example = "[\"WiFi\", \"Pool\", \"Gym\"]")
    private List<String> amenities;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Hotel address details")
    public static class AddressDto {

        @Schema(description = "House number", example = "123")
        private int houseNumber;

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
    @Schema(description = "Hotel contact information")
    public static class ContactsDto {

        @Schema(description = "Phone number", example = "+1-800-123-4567")
        private String phone;

        @Schema(description = "Email address", example = "info@hilton.com")
        private String email;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Arrival and departure times")
    public static class ArrivalTimeDto {

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        @Schema(description = "Check-in time (24h format)", example = "14:00")
        private LocalTime checkIn;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
        @Schema(description = "Check-out time (24h format)", example = "12:00")
        private LocalTime checkOut;
    }
}
