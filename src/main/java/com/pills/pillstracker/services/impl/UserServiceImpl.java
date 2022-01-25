package com.pills.pillstracker.services.impl;

import com.pills.pillstracker.exceptions.UserAlreadyExistException;
import com.pills.pillstracker.models.daos.ERole;
import com.pills.pillstracker.models.daos.Role;
import com.pills.pillstracker.models.daos.User;
import com.pills.pillstracker.repositories.RoleRepository;
import com.pills.pillstracker.repositories.UserRepository;
import com.pills.pillstracker.security.services.impl.UserDetailsImpl;
import com.pills.pillstracker.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Set;


@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getAuthenticatedUser() {

        Long id = ((UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication()
            .getPrincipal())
            .getId();
        return userRepository.getById(id);
    }

    @Override
    public User registerNewUserAccount(User user) throws UserAlreadyExistException {

        checkExistingFields(user);
        Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }

    private void checkExistingFields(User user) throws UserAlreadyExistException {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("register.err.emailInUse");

        } else if (userRepository.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistException("register.err.userNameInUse");

        } else if (userRepository.existsByContactNumber(user.getContactNumber())) {
            throw new UserAlreadyExistException("register.err.contactNumberInUse");
        }
    }

}
