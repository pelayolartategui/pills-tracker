package com.pills.pillstracker.services;

import com.pills.pillstracker.exceptions.UserAlreadyExistException;
import com.pills.pillstracker.models.dao.User;
import com.pills.pillstracker.models.dtos.UserDto;


public interface UserService {

    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;

}
