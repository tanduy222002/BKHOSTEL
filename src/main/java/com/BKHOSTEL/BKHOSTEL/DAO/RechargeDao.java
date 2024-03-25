package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.User;

public interface RechargeDao {

    public Recharge save(Recharge recharge);

    public Recharge findById(String id);
}
