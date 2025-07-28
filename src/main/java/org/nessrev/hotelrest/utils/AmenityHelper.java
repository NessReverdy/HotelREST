package org.nessrev.hotelrest.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.nessrev.hotelrest.entity.AmenityEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AmenityHelper {
    private final EntityManager entityManager;

    public AmenityEntity findByNameIgnoreCase(String name) {
        try {
            return entityManager.createQuery(
                            "SELECT a FROM AmenityEntity a WHERE LOWER(a.name) = :name", AmenityEntity.class)
                    .setParameter("name", name.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
