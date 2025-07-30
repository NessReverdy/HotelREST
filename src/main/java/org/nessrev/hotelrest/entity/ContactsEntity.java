package org.nessrev.hotelrest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "contacts")
@Getter
@Setter
public class ContactsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String email;

    @OneToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;
}
