package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Dto.PaginationDto;
import com.BKHOSTEL.BKHOSTEL.Entity.Recharge;

import java.util.Map;

public interface RechargeDao {

    public Recharge save(Recharge recharge);

    public Recharge findById(String id);

    public PaginationDto findAllSuccessRechargeOfUser(Map<String,Object> filterProps, int size, int page);
}
