package com.BKHOSTEL.BKHOSTEL.DAO;
import com.BKHOSTEL.BKHOSTEL.Entity.Otp;
import org.springframework.data.mongodb.core.MongoTemplate;

public interface OtpDao {



    public Otp save(Otp otp);

}
