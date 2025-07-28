package org.nessrev.hotelrest.repo.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.nessrev.hotelrest.entity.HotelEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class HotelSpecifications {
    public static Specification<HotelEntity> hasName(String name) {
        return (root, query, cb) -> name == null ? null :
                cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<HotelEntity> hasBrand(String brand) {
        return (root, query, cb) -> brand == null ? null :
                cb.equal(cb.lower(root.get("brand")), brand.toLowerCase());
    }

    public static Specification<HotelEntity> hasCity(String city) {
        return (root, query, cb) -> city == null ? null :
                cb.equal(cb.lower(root.get("address").get("city")), city.toLowerCase());
    }

    public static Specification<HotelEntity> hasCountry(String country) {
        return (root, query, cb) -> country == null ? null :
                cb.equal(cb.lower(root.get("address").get("country")), country.toLowerCase());
    }

    public static Specification<HotelEntity> hasAmenities(List<String> amenities) {
        return (root, query, cb) -> {
            if (amenities == null || amenities.isEmpty()) return null;
            Join<Object, Object> join = root.join("amenities", JoinType.LEFT);
            CriteriaBuilder.In<String> inClause = cb.in(cb.lower(join.get("name")));
            for (String amenity : amenities) {
                inClause.value(amenity.toLowerCase());
            }
            query.distinct(true);
            return inClause;
        };
    }
}
