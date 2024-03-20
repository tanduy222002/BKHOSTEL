package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;

import java.util.List;

public interface RentalServiceDao {
    RentalService save(RentalService rentalService);

    List<RentalService> getAllServices();

    RentalService findById(String id);


}
