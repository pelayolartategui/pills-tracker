package com.pills.pillstracker.services;

import com.pills.pillstracker.exceptions.UserAlreadyExistException;
import com.pills.pillstracker.models.daos.User;


public interface UserService {

    User getAuthenticatedUser();

    User registerNewUserAccount(User user) throws UserAlreadyExistException;

}
