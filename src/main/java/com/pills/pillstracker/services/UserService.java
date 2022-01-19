package com.pills.pillstracker.services;

import com.pills.pillstracker.dtos.UserDto;
import com.pills.pillstracker.exceptions.UserAlreadyExistException;
import com.pills.pillstracker.models.User;


public interface UserService {

    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;

}
