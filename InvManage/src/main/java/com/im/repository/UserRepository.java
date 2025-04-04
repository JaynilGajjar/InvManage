package com.im.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.im.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByUsername(String username);
	
	@Query(value = "SELECT u.role FROM userinfo u " +
            "INNER JOIN userrole ur ON u.role::BIGINT = ur.id " +
            "WHERE u.role = :role AND u.id = :id", nativeQuery = true)
	String getUserRoleByID(@Param("role") String role, @Param("id") Long id);

}
