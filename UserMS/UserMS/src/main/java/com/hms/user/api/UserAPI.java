package com.hms.user.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hms.user.dto.LoginDTO;
import com.hms.user.dto.ResponseDTO;
import com.hms.user.dto.UserDTO;
import com.hms.user.exception.HMSUserException;
import com.hms.user.jwt.JwtUtil;
import com.hms.user.service.UserService;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class UserAPI {
    
    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailsService uerDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> regsiterUser(@RequestBody @Valid UserDTO userDTO) throws HMSUserException{
        userService.registerUser(userDTO);
        return new ResponseEntity<>(new ResponseDTO("User Registered"),HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginDTO loginDTO) throws HMSUserException{
        // UserDTO user = userService.loginUser(userDTO);
        // return new ResponseEntity<>(user,HttpStatus.OK);
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));  // password veirifcation is done here
        }catch(AuthenticationException e){
            throw new HMSUserException("INVALID_CREDENTIALS");
        }

        final UserDetails userDetails = uerDetailsService.loadUserByUsername(loginDTO.getEmail());  // now here we are loading the UserDetails
        final String jwt = jwtUtil.generateToken(userDetails);  // now here we generate jwt token
        return new ResponseEntity<>(jwt,HttpStatus.OK);

    } 

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("Test",HttpStatus.OK);
    }
    
    
}
