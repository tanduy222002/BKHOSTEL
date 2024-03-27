package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.RechargeDao;
import com.BKHOSTEL.BKHOSTEL.Dto.PaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.RentalService;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.security.Provider;
import java.util.HashMap;
import java.util.Map;

@Service
@Validated
public class RechargeService {
    private RechargeDao rechargeDaoImpl;
    @Autowired

    public RechargeService(RechargeDao rechargeDaoImpl) {
        this.rechargeDaoImpl = rechargeDaoImpl;
    }
    public PaginationDto getAllRecharge(String userId, String status, int size, int pageIndex){
        Map<String, Object> map = new HashMap<>();
        if(userId != null){
            map.put("userId", new ObjectId(userId));
        }
        if(status != null){
            map.put("status", status);
        }

        return rechargeDaoImpl.findAllSuccessRechargeOfUser(map,size,pageIndex);
    }

    public PaginationDto getRechargeByUserId(String userId, String status, int size, int pageIndex) {
        User user = UserService.getCurrentUserByAuthContextWithId(userId);
        return getAllRecharge(userId,status,size,pageIndex);
    }


}
