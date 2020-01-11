package com.gdkm.Repository;

import com.gdkm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Integer> {
     User findByuserName(String userName);
     User findByMail(String mail);
     @Query(value = "update user set status=?1 where user_name=?2",nativeQuery = true)
     @Modifying
     public void updateOne(Integer status,String userName);

}
