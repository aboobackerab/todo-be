package com.Todo.Repo;

import com.Todo.Entity.Otp;
import com.Todo.Entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {
    Optional<Otp> findByEmail (String email);
}
