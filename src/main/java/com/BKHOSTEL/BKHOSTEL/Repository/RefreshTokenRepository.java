package com.BKHOSTEL.BKHOSTEL.Repository;

import com.BKHOSTEL.BKHOSTEL.Entity.RefreshToken;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    public Optional<RefreshToken> findByToken(String token);
}
