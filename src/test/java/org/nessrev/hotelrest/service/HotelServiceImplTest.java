package org.nessrev.hotelrest.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.nessrev.hotelrest.dto.HotelCreateRequest;
import org.nessrev.hotelrest.dto.HotelFullInfo;
import org.nessrev.hotelrest.dto.HotelShortInfo;
import org.nessrev.hotelrest.entity.*;
import org.nessrev.hotelrest.repo.HotelRepository;
import org.nessrev.hotelrest.service.utils.TestDataHelper;
import org.nessrev.hotelrest.utils.AmenityHelper;
import org.nessrev.hotelrest.utils.HistogramQueryHelper;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HotelServiceImplTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private EntityManager entityManager;
    @Mock
    private AmenityHelper amenityHelper;
    @Mock
    private HistogramQueryHelper histogramQueryHelper;

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Test
    public void getAllHotels_success() {
        HotelEntity hotel1 = TestDataHelper.createSortHotelInfo(1L, "Hotel A", 10, "Minsk", "+375291234567");
        HotelEntity hotel2 = TestDataHelper.createSortHotelInfo(2L, "Hotel B", 20, "Gomel", "+375291112233");

        List<HotelEntity> hotelEntities = List.of(hotel1, hotel2);

        when(hotelRepository.findAll()).thenReturn(hotelEntities);
        List<HotelShortInfo> result = hotelService.getAllHotels();

        assertEquals(2, result.size());
        assertEquals("Hotel A", result.get(0).getName());
        assertEquals("Hotel B", result.get(1).getName());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());

        verify(hotelRepository, times(1)).findAll();
    }

    @Test
    public void getAllHotels_repositoryThrowsException() {
        when(hotelRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> hotelService.getAllHotels());

        assertEquals("Database error", ex.getMessage());

        verify(hotelRepository).findAll();
    }

    @Test
    public void getHotelById_success() {
        Long id = 1L;
        HotelEntity hotel = TestDataHelper.createFullHotelInfo(id,
                "Test Hotel",
                123,
                "Minsk",
                "Belarus",
                "+375291112233",
                "test@example.com",
                LocalTime.of(14, 0),
                LocalTime.of(12, 0),
                List.of("WiFi", "Breakfast"));

        when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));

        HotelFullInfo result = hotelService.getHotelById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("Test Hotel", result.getName());

        verify(hotelRepository).findById(id);
    }

    @Test
    public void getHotelById_notFound_shouldThrowException() {
        Long id = 99L;
        when(hotelRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> hotelService.getHotelById(id));

        assertEquals("Hotel with id 99 not found", exception.getMessage());

        verify(hotelRepository).findById(id);
    }

    @Test
    public void searchHotels_success() {
        String name = "Hotel";
        String brand = "LuxuryBrand";
        String city = "Minsk";
        String country = "Belarus";
        List<String> amenities = List.of("WiFi", "Pool");

        HotelEntity hotel = TestDataHelper.createHotelEntity(1L,
                "Hotel Lux",
                15,
                "Minsk",
                "Belarus",
                "+375291234567",
                "lux@example.com",
                null);

        when(hotelRepository.findAll(Mockito.<Specification<HotelEntity>>any()))
                .thenReturn(List.of(hotel));

        List<HotelShortInfo> result = hotelService.searchHotels(name, brand, city, country, amenities);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Hotel Lux", result.get(0).getName());
    }

    @Test
    public void searchHotels_noResults() {
        when(hotelRepository.findAll(Mockito.<Specification<HotelEntity>>any()))
                .thenReturn(Collections.emptyList());

        List<HotelShortInfo> result = hotelService.searchHotels(
                "NonExistent", null, null, null, null);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        Mockito.verify(hotelRepository).findAll(Mockito.<Specification<HotelEntity>>any());
    }

    @Test
    public void searchHotels_repositoryThrowsException() {
        when(hotelRepository.findAll(Mockito.<Specification<HotelEntity>>any()))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                hotelService.searchHotels("Hotel", null, null, null, null));

        assertEquals("DB error", exception.getMessage());

        Mockito.verify(hotelRepository).findAll(Mockito.<Specification<HotelEntity>>any());
    }

    @Test
    public void createHotel_success() {
        HotelCreateRequest request = new HotelCreateRequest();
        request.setName("New Hotel");
        request.setBrand("BrandX");

        HotelCreateRequest.AddressDto address = new HotelCreateRequest.AddressDto();
        address.setCity("Minsk");
        address.setCountry("Belarus");
        request.setAddress(address);

        HotelEntity savedHotel = TestDataHelper.createSavedHotelEntity(1L,
                "New Hotel",
                12,
                "Minsk",
                "Belarus",
                "+375291122334",
                "new@hotel.com");

        when(hotelRepository.save(any(HotelEntity.class))).thenReturn(savedHotel);

        HotelShortInfo result = hotelService.createHotel(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("New Hotel", result.getName());

        verify(hotelRepository).save(any(HotelEntity.class));
    }

    @Test
    public void createHotel_repositoryThrowsException() {
        HotelCreateRequest request = new HotelCreateRequest();
        request.setName("Hotel Fails");

        when(hotelRepository.save(any(HotelEntity.class)))
                .thenThrow(new RuntimeException("Save failed"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> hotelService.createHotel(request));
        assertEquals("Save failed", ex.getMessage());

        verify(hotelRepository).save(any(HotelEntity.class));
    }

    @Test
    public void addAmenitiesToHotel_success() {
        Long hotelId = 1L;
        List<String> amenities = List.of("WiFi", "Pool");

        HotelEntity hotel = new HotelEntity();
        hotel.setId(hotelId);
        hotel.setAmenities(new ArrayList<>());

        AmenityEntity wifi = new AmenityEntity();
        wifi.setName("WiFi");

        AmenityEntity pool = new AmenityEntity();
        pool.setName("Pool");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(amenityHelper.findByNameIgnoreCase("WiFi")).thenReturn(wifi);
        when(amenityHelper.findByNameIgnoreCase("Pool")).thenReturn(pool);

        hotelService.addAmenitiesToHotel(hotelId, amenities);

        assertEquals(2, hotel.getAmenities().size());
        assertTrue(hotel.getAmenities().contains(wifi));
        assertTrue(hotel.getAmenities().contains(pool));

        verify(hotelRepository).save(hotel);
    }

    @Test
    public void addAmenitiesToHotel_hotelNotFound_shouldThrowException() {
        Long hotelId = 42L;
        List<String> amenities = List.of("WiFi");

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                hotelService.addAmenitiesToHotel(hotelId, amenities));

        assertEquals("Hotel with id 42 not found", exception.getMessage());
        verify(hotelRepository, never()).save(any());
    }

    @Test
    public void addAmenitiesToHotel_amenityNotFound_shouldCreateNewAmenity() {
        Long hotelId = 1L;
        String newAmenityName = "Spa";
        List<String> amenities = List.of(newAmenityName);

        HotelEntity hotel = new HotelEntity();
        hotel.setId(hotelId);
        hotel.setAmenities(new ArrayList<>());

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(amenityHelper.findByNameIgnoreCase(newAmenityName)).thenReturn(null);

        hotelService.addAmenitiesToHotel(hotelId, amenities);

        assertEquals(1, hotel.getAmenities().size());
        assertEquals(newAmenityName, hotel.getAmenities().get(0).getName());

        verify(entityManager).persist(any(AmenityEntity.class));
        verify(hotelRepository).save(hotel);
    }

    @Test
    public void addAmenitiesToHotel_duplicateAmenity_shouldNotAddTwice() {
        Long hotelId = 1L;
        List<String> amenities = List.of("WiFi");

        AmenityEntity wifi = new AmenityEntity();
        wifi.setName("WiFi");

        HotelEntity hotel = new HotelEntity();
        hotel.setId(hotelId);
        hotel.setAmenities(new ArrayList<>(List.of(wifi)));

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(amenityHelper.findByNameIgnoreCase("WiFi")).thenReturn(wifi);

        hotelService.addAmenitiesToHotel(hotelId, amenities);

        assertEquals(1, hotel.getAmenities().size());
        verify(hotelRepository).save(hotel);
    }

    @Test
    public void getHotelHistogram_brand_success() {
        Map<String, Long> expected = Map.of("Hilton", 5L, "Marriott", 3L);

        when(histogramQueryHelper.getSimpleHistogram(anyString())).thenReturn(expected);

        Map<String, Long> result = hotelService.getHotelHistogram("brand");

        assertEquals(expected, result);
        verify(histogramQueryHelper).getSimpleHistogram("SELECT h.brand, COUNT(h) FROM HotelEntity h GROUP BY h.brand");
    }

    @Test
    public void getHotelHistogram_city_success() {
        Map<String, Long> expected = Map.of("Minsk", 4L, "Gomel", 2L);

        when(histogramQueryHelper.getSimpleHistogram(anyString())).thenReturn(expected);

        Map<String, Long> result = hotelService.getHotelHistogram("city");

        assertEquals(expected, result);
        verify(histogramQueryHelper).getSimpleHistogram("SELECT h.address.city, COUNT(h) FROM HotelEntity h GROUP BY h.address.city");
    }

    @Test
    public void getHotelHistogram_country_success() {
        Map<String, Long> expected = Map.of("Belarus", 6L, "Poland", 1L);

        when(histogramQueryHelper.getSimpleHistogram(anyString())).thenReturn(expected);

        Map<String, Long> result = hotelService.getHotelHistogram("country");

        assertEquals(expected, result);
        verify(histogramQueryHelper).getSimpleHistogram("SELECT h.address.country, COUNT(h) FROM HotelEntity h GROUP BY h.address.country");
    }

    @Test
    public void getHotelHistogram_amenities_success() {
        Map<String, Long> expected = Map.of("WiFi", 7L, "Pool", 2L);

        when(histogramQueryHelper.getAmenitiesHistogram()).thenReturn(expected);

        Map<String, Long> result = hotelService.getHotelHistogram("amenities");

        assertEquals(expected, result);
        verify(histogramQueryHelper).getAmenitiesHistogram();
    }

    @Test
    public void getHotelHistogram_unsupportedParam_shouldThrowException() {
        String invalidParam = "stars";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                hotelService.getHotelHistogram(invalidParam));

        assertEquals("Unsupported histogram param: stars", exception.getMessage());
        verifyNoInteractions(histogramQueryHelper);
    }

}
