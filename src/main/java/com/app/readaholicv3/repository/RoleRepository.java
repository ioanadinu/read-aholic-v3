package com.app.readaholicv3.repository;

import com.app.readaholicv3.model.Role;
import com.app.readaholicv3.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleName roleName);
}
