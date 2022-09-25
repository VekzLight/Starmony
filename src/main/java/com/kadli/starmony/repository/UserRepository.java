package com.kadli.starmony.repository;

import com.kadli.starmony.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :#{#user.username} AND u.password = :#{#user.password}")
    boolean verificarUsuario(@Param("user") User user);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.email = :email")
    boolean existEmail(@Param("email") String email);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.username = :username")
    boolean existUsername(@Param("username") String username);

    @Query("FROM User u WHERE u.username = :username")
    User findByUsername(@Param("username") String username);
}
