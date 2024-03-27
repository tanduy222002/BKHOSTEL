package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.RentalServiceDao;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return list;
    }

    public void deleteById(String id){
        if(rentalServiceDaoImpl.deleteById(id)==0){
            throw new NotFoundException("Rental service not found");
        }
    }
    @Transactional
    public RentalService updateValues(String rentalId, String name, Double dailyPrice, Double weeklyPrice, Double monthlyPrice, String desc,String suitableFor){
        Map<String,Object> props =new HashMap();
        if(name!=null&&!name.isBlank()){
            props.put("category",name);
        }
        if(dailyPrice!=null){
            props.put("dailyPrice",dailyPrice);
        }
        if(weeklyPrice!=null){
            props.put("weeklyPrice",weeklyPrice);
        }
        if(monthlyPrice!=null){
            props.put("monthlyPrice",monthlyPrice);
        }
        if(desc!=null){
            props.put("desc",desc);
        }
        if(suitableFor!=null){
            props.put("suitableFor",suitableFor);
        }

        RentalService service= rentalServiceDaoImpl.updateValue(rentalId,props);
        if(service==null){
            throw new NotFoundException("Service not found");
        }
        return service;
    }

    public static double calculateServiceCost(RentalService service, int numOfdays){
        double dailyPrice= service.getDailyPrice();
        double weeklyPrice= service.getWeeklyPrice();
        double monthlyPrice=service.getMonthlyPrice();
        double total=0;
        if(numOfdays>=30){
            total += numOfdays/30 *monthlyPrice;
            numOfdays=numOfdays%30;
        }

        if(numOfdays>=7){
            total += numOfdays/7 *weeklyPrice;
            numOfdays=numOfdays%7;
        }

        total += dailyPrice*numOfdays;
        return total;

    }
}
