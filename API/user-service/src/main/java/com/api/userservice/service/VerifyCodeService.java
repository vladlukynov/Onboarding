package com.api.userservice.service;

import com.api.userservice.model.User;
import com.api.userservice.model.VerifyCode;

public interface VerifyCodeService {
    VerifyCode findVerifyCodeByUser(User user);

    VerifyCode findVerifyCodeByUserAndCode(String email, String code);

    void deleteVerifyCodeByUser(User user);

    void saveCode(VerifyCode verifyCode);
}
