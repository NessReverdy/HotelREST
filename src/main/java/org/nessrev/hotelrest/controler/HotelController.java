package org.nessrev.hotelrest.controler;

import org.nessrev.hotelrest.dto.HotelCreateRequest;
import org.nessrev.hotelrest.dto.HotelFullInfo;
import org.nessrev.hotelrest.dto.HotelShortInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/property-view")
public interface HotelController {
    @GetMapping("/hotels")
    ResponseEntity<List<HotelShortInfo>> getAllHotels();

    @GetMapping("/hotels/{id}")
    ResponseEntity<HotelFullInfo> getHotelById(@PathVariable Long id);

    @GetMapping("/search")
    ResponseEntity<List<HotelShortInfo>> searchHotels(@RequestParam(required = false) String name,
                                      @RequestParam(required = false) String brand,
                                      @RequestParam(required = false) String city,
                                      @RequestParam(required = false) String country,
                                      @RequestParam(required = false) List<String> amenities);

    @PostMapping("/hotels")
    ResponseEntity<HotelShortInfo> createHotel(@RequestBody HotelCreateRequest request);

    @PostMapping("/hotels/{id}/amenities")
    ResponseEntity<Void> addAmenitiesToHotel(@PathVariable Long id, @RequestBody List<String> amenities);

    @GetMapping("/histogram/{param}")
    ResponseEntity<Map<String, Long>> getHotelHistogram(@PathVariable String param);

}
