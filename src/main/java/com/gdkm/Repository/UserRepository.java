package com.gdkm.Repository;

import com.gdkm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
     User findByuserName(String userName);
     User findByMail(String mail);

}
