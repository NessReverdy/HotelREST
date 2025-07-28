package org.nessrev.hotelrest.service;

import org.nessrev.hotelrest.dto.HotelCreateRequest;
import org.nessrev.hotelrest.dto.HotelFullInfo;
import org.nessrev.hotelrest.dto.HotelShortInfo;

import java.util.List;
import java.util.Map;

public interface HotelService {
    List<HotelShortInfo> getAllHotels();
    HotelFullInfo getHotelById(Long id);
    List<HotelShortInfo> searchHotels(String name, String brand, String city, String country, List<String> amenities);
    HotelShortInfo createHotel(HotelCreateRequest request);
    void addAmenitiesToHotel(Long hotelId, List<String> amenities);
    Map<String, Long> getHotelHistogram(String param);
}
