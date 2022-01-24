package com.pills.pillstracker.services.impl;

import com.pills.pillstracker.exceptions.UserAlreadyExistException;
import com.pills.pillstracker.models.dao.ERole;
import com.pills.pillstracker.models.dao.Role;
import com.pills.pillstracker.models.dao.User;
import com.pills.pillstracker.models.dtos.UserDto;
import com.pills.pillstracker.repositories.RoleRepository;
import com.pills.pillstracker.repositories.UserRepository;
import com.pills.pillstracker.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Set;


@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {

        checkExistingFields(userDto);

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstname(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setContactNumber(userDto.getContactNumber());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }

    private void checkExistingFields(UserDto userDto) throws UserAlreadyExistException {

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new UserAlreadyExistException("register.err.emailInUse");

        } else if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserAlreadyExistException("register.err.userNameInUse");

        } else if (userRepository.existsByContactNumber(userDto.getContactNumber())) {
            throw new UserAlreadyExistException("register.err.contactNumberInUse");
        }
    }

}
