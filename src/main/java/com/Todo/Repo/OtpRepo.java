package com.Todo.Repo;

import com.Todo.Entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepo extends JpaRepository<Otp, Long> {
}
