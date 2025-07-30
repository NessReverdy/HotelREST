package org.nessrev.hotelrest.controler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.nessrev.hotelrest.dto.HotelCreateRequest;
import org.nessrev.hotelrest.dto.HotelFullInfo;
import org.nessrev.hotelrest.dto.HotelShortInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Tag(name = "Hotels", description = "Endpoints for managing hotels")
@RequestMapping("/property-view")
public interface HotelController {

    @Operation(
            summary = "Get all hotels",
            description = "Returns a list of all hotels with basic information"
    )
    @GetMapping("/hotels")
    ResponseEntity<List<HotelShortInfo>> getAllHotels();

    @Operation(
            summary = "Get hotel by ID",
            description = "Returns full information about the hotel with the given ID"
    )
    @ApiResponse(responseCode = "200", description = "Hotel found")
    @ApiResponse(responseCode = "404", description = "Hotel not found")
    @GetMapping("/hotels/{id}")
    ResponseEntity<HotelFullInfo> getHotelById(
            @Parameter(description = "Hotel ID") @PathVariable Long id);

    @Operation(
            summary = "Search hotels",
            description = "Searches hotels by optional parameters like name, brand, city, country, and amenities"
    )
    @GetMapping("/search")
    ResponseEntity<List<HotelShortInfo>> searchHotels(
            @Parameter(description = "Hotel name") @RequestParam(required = false) String name,
            @Parameter(description = "Hotel brand") @RequestParam(required = false) String brand,
            @Parameter(description = "City where the hotel is located") @RequestParam(required = false) String city,
            @Parameter(description = "Country where the hotel is located") @RequestParam(required = false) String country,
            @Parameter(description = "List of required amenities") @RequestParam(required = false) List<String> amenities
    );

    @Operation(
            summary = "Create a new hotel",
            description = "Creates a hotel using the given request body"
    )
    @PostMapping("/hotels")
    ResponseEntity<HotelShortInfo> createHotel(
            @RequestBody HotelCreateRequest request);

    @Operation(
            summary = "Add amenities to a hotel",
            description = "Adds new amenities to an existing hotel by ID"
    )
    @PostMapping("/hotels/{id}/amenities")
    ResponseEntity<Void> addAmenitiesToHotel(
            @Parameter(description = "Hotel ID") @PathVariable Long id,
            @RequestBody List<String> amenities);

    @Operation(
            summary = "Get histogram of hotel field values",
            description = "Returns a histogram (e.g., count per city or country) based on the given parameter"
    )
    @GetMapping("/histogram/{param}")
    ResponseEntity<Map<String, Long>> getHotelHistogram(
            @Parameter(description = "Field name to build histogram by") @PathVariable String param);
}