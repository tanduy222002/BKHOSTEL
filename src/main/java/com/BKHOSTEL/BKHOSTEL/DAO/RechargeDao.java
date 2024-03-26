package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Dto.RechargePaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;
import com.BKHOSTEL.BKHOSTEL.Entity.User;

import java.util.List;
import java.util.Map;

public interface RechargeDao {

    public Recharge save(Recharge recharge);

    public Recharge findById(String id);

    public RechargePaginationDto findAllSuccessRechargeOfUser(Map<String,Object> filterProps, int size, int page);
}
