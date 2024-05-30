package com.niit.UserAuthentication.security;



import com.niit.UserAuthentication.domain.User;

import java.util.Map;


public interface SecurityTokenGenerator {
//    String createToken(User user);
    Map<String,String> generateToken(User user);
}
