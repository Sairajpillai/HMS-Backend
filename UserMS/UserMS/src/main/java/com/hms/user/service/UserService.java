package com.hms.user.service;

import com.hms.user.dto.UserDTO;
import com.hms.user.exception.HMSUserException;

public interface UserService {
    public void registerUser(UserDTO userDTO) throws HMSUserException;
    public UserDTO loginUser(UserDTO userDTO) throws HMSUserException;
    public UserDTO getUserById(Long id) throws HMSUserException;
    public void updateUser(UserDTO userDTO);
    public UserDTO getUser(String email) throws HMSUserException;
}
