package com.example.demo.repository;

import com.example.demo.entity.User;
import org.hibernate.annotations.Where;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

  boolean existsByUsername(String username);
  boolean existsByEmail(String email);

  User findByEmail(String email);
  User findByUsername(String username);

  @Query("SELECT u FROM User u WHERE lower(u.username) LIKE LOWER(CONCAT('%', :name, '%'))")
  Page<User> searchByName(@Param("name") String name, Pageable pageable);

  @Query("SELECT u FROM User u")
  Page<User> getAllUser(Pageable pageable);

  @Transactional
  @Modifying
  @Query("UPDATE User u SET u.deletedAt = CURRENT_TIMESTAMP WHERE u.id = :id")
  void softDelete(@Param("id") UUID id);
}
