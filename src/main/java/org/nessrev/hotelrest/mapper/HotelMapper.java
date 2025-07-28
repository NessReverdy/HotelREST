package org.nessrev.hotelrest.mapper;

import org.nessrev.hotelrest.dto.HotelCreateRequest;
import org.nessrev.hotelrest.dto.HotelFullInfo;
import org.nessrev.hotelrest.dto.HotelShortInfo;
import org.nessrev.hotelrest.entity.*;

public class HotelMapper {
    public static HotelShortInfo toShortInfo(HotelEntity hotel) {
        return new HotelShortInfo(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                formatAddress(hotel),
                hotel.getContacts().getPhone()
        );
    }

    private static String formatAddress(HotelEntity hotel) {
        AddressEntity address = hotel.getAddress();
        return String.format("%s %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode());
    }

    public static HotelFullInfo toFullInfo(HotelEntity hotel) {
        return new HotelFullInfo(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),

                new HotelFullInfo.AddressDto(
                        hotel.getAddress().getHouseNumber(),
                        hotel.getAddress().getStreet(),
                        hotel.getAddress().getCity(),
                        hotel.getAddress().getCountry(),
                        hotel.getAddress().getPostCode()
                ),

                new HotelFullInfo.ContactsDto(
                        hotel.getContacts().getPhone(),
                        hotel.getContacts().getEmail()
                ),

                new HotelFullInfo.ArrivalTimeDto(
                        hotel.getArrivalTime().getCheckIn(),
                        hotel.getArrivalTime().getCheckOut()
                ),

                hotel.getAmenities().stream()
                        .map(AmenityEntity::getName)
                        .toList()
        );
    }

    public static HotelEntity fromCreateRequest(HotelCreateRequest request) {
        HotelEntity hotel = new HotelEntity();
        hotel.setName(request.getName());
        hotel.setDescription(request.getDescription());
        hotel.setBrand(request.getBrand());
        hotel.setAddress(toAddressEntity(request.getAddress()));
        hotel.setContacts(toContactsEntity(request.getContacts()));
        hotel.setArrivalTime(toArrivalTimeEntity(request.getArrivalTime()));
        return hotel;
    }

    private static AddressEntity toAddressEntity(HotelCreateRequest.AddressDto dto) {
        if (dto == null) return null;
        AddressEntity address = new AddressEntity();
        address.setHouseNumber(dto.getHouseNumber());
        address.setStreet(dto.getStreet());
        address.setCity(dto.getCity());
        address.setCountry(dto.getCountry());
        address.setPostCode(dto.getPostCode());
        return address;
    }

    private static ContactsEntity toContactsEntity(HotelCreateRequest.ContactsDto dto) {
        if (dto == null) return null;
        ContactsEntity contacts = new ContactsEntity();
        contacts.setPhone(dto.getPhoneNumber());
        contacts.setEmail(dto.getEmail());
        return contacts;
    }

    private static ArrivalTimeEntity toArrivalTimeEntity(HotelCreateRequest.ArrivalTimeDto dto) {
        if (dto == null) return null;
        ArrivalTimeEntity arrivalTime = new ArrivalTimeEntity();
        arrivalTime.setCheckIn(dto.getCheckIn());
        arrivalTime.setCheckOut(dto.getCheckOut());
        return arrivalTime;
    }

}
