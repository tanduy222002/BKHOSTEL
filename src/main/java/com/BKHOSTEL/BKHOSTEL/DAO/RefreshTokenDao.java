package com.BKHOSTEL.BKHOSTEL.DAO;

import com.BKHOSTEL.BKHOSTEL.Entity.RefreshToken;

public interface RefreshTokenDao {
    public void delete(RefreshToken refreshToken);
    public RefreshToken findByToken(String token);
}
