package com.manage.company.company.repository;

import com.manage.company.company.entity.Role;
import com.manage.company.company.entity.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
