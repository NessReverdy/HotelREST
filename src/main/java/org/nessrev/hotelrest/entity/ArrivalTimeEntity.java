package org.nessrev.hotelrest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "arrival_time")
@Getter
@Setter
public class ArrivalTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in")
    private String checkIn;
    @Column(name = "check_out")
    private String checkOut;

    @OneToOne(mappedBy = "arrivalTime")
    @Column(name = "hotel_id", nullable = false)
    private HotelEntity hotelId;
}
