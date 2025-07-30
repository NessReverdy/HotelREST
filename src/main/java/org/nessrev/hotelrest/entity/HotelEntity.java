package org.nessrev.hotelrest.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "hotels")
@Getter
@Setter
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String brand;

    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    private AddressEntity address;

    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    private ContactsEntity contacts;

    @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL)
    private ArrivalTimeEntity arrivalTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "hotel_amenities",
            joinColumns = @JoinColumn(name = "hotel_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private List<AmenityEntity> amenities;

}
