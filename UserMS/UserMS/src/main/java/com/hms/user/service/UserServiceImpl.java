package com.hms.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hms.user.clients.ProfileClient;
import com.hms.user.dto.Roles;
import com.hms.user.dto.UserDTO;
import com.hms.user.entity.User;
import com.hms.user.exception.HMSUserException;
import com.hms.user.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ApiService apiService;

    @Autowired
    private ProfileClient profileClient;

    @Override
    public void registerUser(UserDTO userDTO) throws HMSUserException {
        Optional<User> opt = userRepository.findByEmail(userDTO.getEmail());
        if(opt.isPresent()){
            throw new HMSUserException("USER_ALREADY_EXIST");
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // Long profileId = apiService.addProfile(userDTO).block();
        Long profileId = null;
        if(userDTO.getRole().equals(Roles.DOCTOR)){
            profileId = profileClient.addDoctor(userDTO);
        }else if(userDTO.getRole().equals(Roles.PATIENT)){
            profileId = profileClient.addPatient(userDTO);
        }

        System.out.println(profileId);
        userDTO.setProfileId(profileId);
        userRepository.save(userDTO.toEntity());
    }

    @Override
    public UserDTO loginUser(UserDTO userDTO) throws HMSUserException {
        User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(()->new HMSUserException("USER_NOT_FOUND"));
        if(!passwordEncoder.matches(userDTO.getPassword(), user.getPassword())){
            throw new HMSUserException("INVALID_CREDENTIALS");
        }
        user.setPassword(null);
        return user.toDTO();
    }

    @Override
    public UserDTO getUserById(Long id) throws HMSUserException {
        return userRepository.findById(id).orElseThrow(()->new HMSUserException("NO_USER_FOUND")).toDTO();
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public UserDTO getUser(String email) throws HMSUserException {
        return userRepository.findByEmail(email).orElseThrow(()-> new HMSUserException("USER_NOT_FOUND")).toDTO();
    }
}
