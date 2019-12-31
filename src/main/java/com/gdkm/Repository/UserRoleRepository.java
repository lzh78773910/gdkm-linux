package com.gdkm.Repository;

import com.gdkm.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {
    List<UserRole> findAllByUserId(Integer adminId);
}
