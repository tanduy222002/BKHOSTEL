package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.User;

import java.util.List;

public interface RechargeDao {

    public Recharge save(Recharge recharge);

    public Recharge findById(String id);

    public List<Recharge> findAllSuccessRechargeOfUser(String userId);
}
