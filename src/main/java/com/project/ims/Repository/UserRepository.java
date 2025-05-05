package com.project.ims.Repository;

import com.project.ims.Model.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    
    User findByemail(String email);
    
    Optional<User> findByusername(String username);
    
    Optional<User> findByEmail(String email);
    
    List<User> findByUsernameContainingIgnoreCase(String username);
}