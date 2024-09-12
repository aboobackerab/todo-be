package com.Todo.Repo;

import com.Todo.Entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserDataRepo extends JpaRepository<UserData,Long> {

    Optional<UserData> findByUsername(String username);
    Optional<UserData> findByEmail (String email);
}
