package com.gdkm.Repository;

import com.gdkm.model.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRoleRepository extends JpaRepository<AdminRole,Integer> {
    List<AdminRole> findAdminRoleByAdminId(Integer adminId);
    List<Integer>  findRoleIdByAdminId(Integer adminId);
}
