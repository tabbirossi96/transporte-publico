package com.demo.UserApi.repositories;

import com.demo.UserApi.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

}
