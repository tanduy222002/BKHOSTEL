package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;

import java.util.List;
import java.util.Map;

public interface RentalServiceDao {
    RentalService save(RentalService rentalService);

    List<RentalService> getAllServices();

    RentalService findById(String id);

    RentalService updateValue(String id, Map<String, Object> props);

    long deleteById(String id);

}
