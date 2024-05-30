package com.niit.UserRegistrationService.proxy;



import com.niit.UserRegistrationService.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="UserAuthentication",url="localhost:8083")
public interface UserProxy {

    @PostMapping("/api/auth/save")
    public ResponseEntity<?> saveUser(@RequestBody User user);
}
