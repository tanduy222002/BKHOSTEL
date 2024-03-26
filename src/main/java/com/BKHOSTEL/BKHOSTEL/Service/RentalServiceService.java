package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.RentalServiceDao;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalServiceService {
    private RentalServiceDao rentalServiceDaoImpl;

    @Autowired
    public RentalServiceService(RentalServiceDao rentalService) {
        this.rentalServiceDaoImpl = rentalService;
    }

    public String createService(RentalService service){
            rentalServiceDaoImpl.save(service);
        return "new rental service added successfully";
    }
    public List<RentalService> getAllServices(){
        List<RentalService> list =rentalServiceDaoImpl.getAllServices();
        System.out.println(list);
        return list;
    }
}
