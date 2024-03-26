package com.BKHOSTEL.BKHOSTEL.Service;

import com.BKHOSTEL.BKHOSTEL.DAO.RechargeDao;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RechargeService {
    private RechargeDao rechargeDaoImpl;
    @Autowired

    public RechargeService(RechargeDao rechargeDaoImpl) {
        this.rechargeDaoImpl = rechargeDaoImpl;
    }
    public List<Recharge> getRechargeByUser(String userId){
        User user=UserService.getCurrentUserByAuthContextWithId(userId);
        return rechargeDaoImpl.findAllSuccessRechargeOfUser(userId);
    }
}
