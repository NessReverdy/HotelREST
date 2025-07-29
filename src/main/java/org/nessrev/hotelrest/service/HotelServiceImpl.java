package org.nessrev.hotelrest.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.nessrev.hotelrest.utils.AmenityHelper;
import org.nessrev.hotelrest.dto.HotelCreateRequest;
import org.nessrev.hotelrest.dto.HotelFullInfo;
import org.nessrev.hotelrest.dto.HotelShortInfo;
import org.nessrev.hotelrest.entity.AmenityEntity;
import org.nessrev.hotelrest.entity.HotelEntity;
import org.nessrev.hotelrest.mapper.HotelMapper;
import org.nessrev.hotelrest.repo.HotelRepository;
import org.nessrev.hotelrest.repo.specification.HotelSpecifications;
import org.nessrev.hotelrest.utils.HistogramQueryHelper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelRepository hotelRepository;
    private final EntityManager entityManager;
    private final AmenityHelper amenityHelper;
    private final HistogramQueryHelper histogramQueryHelper;

    public HotelServiceImpl(HotelRepository hotelRepository,
                            EntityManager entityManager,
                            AmenityHelper amenityHelper,
                            HistogramQueryHelper histogramQueryHelper) {
        this.hotelRepository = hotelRepository;
        this.entityManager = entityManager;
        this.amenityHelper = amenityHelper;
        this.histogramQueryHelper = histogramQueryHelper;
    }

    @Override
    public List<HotelShortInfo> getAllHotels() {
        return hotelRepository.findAll().stream()
                .map(HotelMapper::toShortInfo)
                .toList();
    }

    @Override
    public HotelFullInfo getHotelById(Long id) {
        HotelEntity hotel = hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel with id " + id + " not found"));

        return HotelMapper.toFullInfo(hotel);
    }

    @Override
    public List<HotelShortInfo> searchHotels(String name,
                                             String brand,
                                             String city,
                                             String country,
                                             List<String> amenities) {
        Specification<HotelEntity> spec = HotelSpecifications.hasName(name)
                .and(HotelSpecifications.hasBrand(brand))
                .and(HotelSpecifications.hasCity(city))
                .and(HotelSpecifications.hasCountry(country))
                .and(HotelSpecifications.hasAmenities(amenities));


        return hotelRepository.findAll(spec).stream()
                .map(HotelMapper::toShortInfo)
                .toList();
    }

    @Override
    @Transactional
    public HotelShortInfo createHotel(HotelCreateRequest request) {
        HotelEntity hotel = HotelMapper.fromCreateRequest(request);
        HotelEntity savedHotel = hotelRepository.save(hotel);

        return HotelMapper.toShortInfo(savedHotel);
    }


    @Override
    @Transactional
    public void addAmenitiesToHotel(Long hotelId, List<String> amenities) {
        HotelEntity hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new EntityNotFoundException("Hotel with id " + hotelId + " not found"));

        if (hotel.getAmenities() == null) {
            hotel.setAmenities(new ArrayList<>());
        }

        for (String amenityName : amenities) {
            AmenityEntity amenity = amenityHelper.findByNameIgnoreCase(amenityName);

            if (amenity == null) {
                amenity = new AmenityEntity();
                amenity.setName(amenityName);
                entityManager.persist(amenity);
            }

            if (!hotel.getAmenities().contains(amenity)) {
                hotel.getAmenities().add(amenity);
            }
        }

        hotelRepository.save(hotel);
    }

    @Override
    public Map<String, Long> getHotelHistogram(String param) {
        return switch (param.toLowerCase()) {
            case "brand" ->
                    histogramQueryHelper.getSimpleHistogram("SELECT h.brand, COUNT(h) FROM HotelEntity h GROUP BY h.brand");
            case "city" ->
                    histogramQueryHelper.getSimpleHistogram("SELECT h.address.city, COUNT(h) FROM HotelEntity h GROUP BY h.address.city");
            case "country" ->
                    histogramQueryHelper.getSimpleHistogram("SELECT h.address.country, COUNT(h) FROM HotelEntity h GROUP BY h.address.country");
            case "amenities" -> histogramQueryHelper.getAmenitiesHistogram();
            default -> throw new IllegalArgumentException("Unsupported histogram param: " + param);
        };
    }
}
