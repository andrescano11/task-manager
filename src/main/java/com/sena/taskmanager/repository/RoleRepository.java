package com.sena.taskmanager.repository;

import com.sena.taskmanager.entity.Role;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
    List<Role> findByNameIn(List<String> names);
    List<Role> findByIdIn(List<Long> ids);
}
