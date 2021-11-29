package org.example.cache;

import org.example.entity.User;

public interface UserCacheService {
    void setUser(User user);

    User getUserBuUserId(String userId);

    void deleteUserByUserId(String userId);

    User getUserByUsername(String username);

    void setPhoneVerifyCode(String phone, String verifyCode);

    String getPhoneVerifyCode(String phone);

    void deletePhoneVerifyCode(String phone);

    void setImageVerifyCode(String account, String verifyCode);

    String getImageVerifyCode(String account);

    void deleteImageVerifyCode(String account);
}