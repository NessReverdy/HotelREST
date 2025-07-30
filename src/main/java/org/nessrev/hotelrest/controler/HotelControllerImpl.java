package org.nessrev.hotelrest.controler;

import org.nessrev.hotelrest.dto.HotelCreateRequest;
import org.nessrev.hotelrest.dto.HotelFullInfo;
import org.nessrev.hotelrest.dto.HotelShortInfo;
import org.nessrev.hotelrest.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/property-view")
public class HotelControllerImpl implements HotelController {
    private final HotelService hotelService;

    public HotelControllerImpl(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Override
    public ResponseEntity<List<HotelShortInfo>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @Override
    public ResponseEntity<HotelFullInfo> getHotelById(Long id) {
        return ResponseEntity.ok(hotelService.getHotelById(id));
    }

    @Override
    public ResponseEntity<List<HotelShortInfo>> searchHotels(String name,
                                             String brand,
                                             String city,
                                             String country,
                                             List<String> amenities) {
        return ResponseEntity.ok(hotelService.searchHotels(name, brand, city, country, amenities));
    }

    @Override
    public ResponseEntity<HotelShortInfo> createHotel(HotelCreateRequest request) {
        return ResponseEntity.ok(hotelService.createHotel(request));
    }

    @Override
    public ResponseEntity<Void> addAmenitiesToHotel(Long id, List<String> amenities) {
        hotelService.addAmenitiesToHotel(id, amenities);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Map<String, Long>> getHotelHistogram(String param) {
        return ResponseEntity.ok(hotelService.getHotelHistogram(param));
    }
}
