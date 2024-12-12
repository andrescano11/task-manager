package com.sena.taskmanager.repository;

import com.sena.taskmanager.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> getRoleByName(String name);

}
