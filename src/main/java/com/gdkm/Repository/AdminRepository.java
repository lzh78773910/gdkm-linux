package com.gdkm.Repository;

import com.gdkm.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer> {
    Admin findByAdminName(String adminName);
}
