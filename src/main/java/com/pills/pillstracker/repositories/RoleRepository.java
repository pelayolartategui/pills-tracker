package com.pills.pillstracker.repositories;

import com.pills.pillstracker.models.dao.ERole;
import com.pills.pillstracker.models.dao.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

}
