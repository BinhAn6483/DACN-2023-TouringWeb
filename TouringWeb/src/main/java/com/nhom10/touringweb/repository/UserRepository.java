package com.nhom10.touringweb.repository;


import com.nhom10.touringweb.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String email);
	User getUserById(int id);

	@Query("SELECT u.password FROM User u WHERE u.email=?1")
	String getPass(String email);

	@Query("SELECT COUNT(u) FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
	int countUserWithRoleUser();

	@Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_USER'")
	List<User> getAllUserByRoleUser();

	@Query("SELECT u.name FROM User u WHERE u.email=?1")
	String getNameByEmail(String email);



}
