package com.hms.user.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hms.user.dto.UserDTO;


@FeignClient(name="ProfileMS")
public interface ProfileClient {
    
    @PostMapping("/profile/doctor/add")
    Long addDoctor(@RequestBody UserDTO userDTO);

    @PostMapping("/patient/doctor/add")
    Long addPatient(@RequestBody UserDTO userDTO);

}
