package org.nessrev.hotelrest.service.utils;

import org.nessrev.hotelrest.entity.*;

import java.time.LocalTime;
import java.util.List;

public class TestDataHelper {
    public static HotelEntity createSortHotelInfo(Long id,
                                                  String name,
                                                  Integer houseNumber,
                                                  String city,
                                                  String phone) {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(id);
        hotel.setName(name);

        AddressEntity address = new AddressEntity();
        address.setHouseNumber(houseNumber);
        address.setCity(city);
        hotel.setAddress(address);

        ContactsEntity contacts = new ContactsEntity();
        contacts.setPhone(phone);
        hotel.setContacts(contacts);

        return hotel;
    }

    public static HotelEntity createFullHotelInfo(Long id,
                                                  String name,
                                                  Integer houseNumber,
                                                  String city,
                                                  String country,
                                                  String phone,
                                                  String email,
                                                  LocalTime checkIn,
                                                  LocalTime checkOut,
                                                  List<String> amenitiesNames) {

        HotelEntity hotel = new HotelEntity();
        hotel.setId(id);
        hotel.setName(name);

        AddressEntity address = new AddressEntity();
        address.setHouseNumber(houseNumber);
        address.setCity(city);
        address.setCountry(country);
        hotel.setAddress(address);

        ContactsEntity contacts = new ContactsEntity();
        contacts.setPhone(phone);
        contacts.setEmail(email);
        hotel.setContacts(contacts);

        ArrivalTimeEntity arrivalTime = new ArrivalTimeEntity();
        arrivalTime.setCheckIn(checkIn);
        arrivalTime.setCheckOut(checkOut);
        hotel.setArrivalTime(arrivalTime);

        List<AmenityEntity> amenities = amenitiesNames.stream()
                .map(nameAmenity -> {
                    AmenityEntity amenity = new AmenityEntity();
                    amenity.setName(nameAmenity);
                    return amenity;
                })
                .toList();

        hotel.setAmenities(amenities);

        return hotel;
    }

    public static HotelEntity createHotelEntity(Long id,
                                                String name,
                                                Integer houseNumber,
                                                String city,
                                                String country,
                                                String phone,
                                                String email,
                                                List<AmenityEntity> amenities) {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(id);
        hotel.setName(name);

        AddressEntity address = new AddressEntity();
        address.setHouseNumber(houseNumber);
        address.setCity(city);
        address.setCountry(country);
        hotel.setAddress(address);

        ContactsEntity contacts = new ContactsEntity();
        contacts.setPhone(phone);
        contacts.setEmail(email);
        hotel.setContacts(contacts);

        if (amenities != null) {
            hotel.setAmenities(amenities);
        }

        return hotel;
    }

    public static HotelEntity createSavedHotelEntity(Long id,
                                                     String name,
                                                     Integer houseNumber,
                                                     String city,
                                                     String country,
                                                     String phone,
                                                     String email) {
        HotelEntity hotel = new HotelEntity();
        hotel.setId(id);
        hotel.setName(name);

        AddressEntity address = new AddressEntity();
        address.setHouseNumber(houseNumber);
        address.setCity(city);
        address.setCountry(country);
        hotel.setAddress(address);

        ContactsEntity contacts = new ContactsEntity();
        contacts.setPhone(phone);
        contacts.setEmail(email);
        hotel.setContacts(contacts);

        return hotel;
    }


}
