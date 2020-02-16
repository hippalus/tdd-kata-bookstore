package com.bookstore.infrastructure.repository;

import com.bookstore.domain.model.City;
import com.bookstore.domain.valueobject.CityNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, CityNumber> {
}
