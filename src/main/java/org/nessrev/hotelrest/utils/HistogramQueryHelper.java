package org.nessrev.hotelrest.utils;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class HistogramQueryHelper{
    private final EntityManager entityManager;

    public Map<String, Long> getSimpleHistogram(String jpql) {
        List<Object[]> results = entityManager.createQuery(jpql, Object[].class).getResultList();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }

    public Map<String, Long> getAmenitiesHistogram() {
        String jpql = "SELECT a.name, COUNT(h) FROM HotelEntity h JOIN h.amenities a GROUP BY a.name";
        List<Object[]> results = entityManager.createQuery(jpql, Object[].class).getResultList();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }
}
