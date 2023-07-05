package com.guangyao.bookstore.warehouse.domain;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface AdvertisementRepository extends CrudRepository<Advertisement, Integer> {

    Iterable<Advertisement> findAll() throws DataAccessException;
}
