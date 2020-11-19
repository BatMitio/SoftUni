package com.gamestore.gamestore.repositories;

import com.gamestore.gamestore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(value = "INSERT INTO users (email, password, full_name) VALUES (1?, 2?, 3?)", nativeQuery = true)
    void registerUser(String email, String password, String fullName);

    @Query(value = "SELECT COUNT(u) FROM User u WHERE u.email = ?1")
    boolean emailExists(String email);

    User getUserByEmailAndPassword(String email, String password);

    User getUserByEmail(String email);
}
