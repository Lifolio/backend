package com.example.lifolio.repository;

import com.example.lifolio.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByUsername(String username);

//    @EntityGraph(attributePaths = "authorities")
//    Optional<User> findOneWithAuthoritiesById(Long userId);



    User findByusername(String username);

    //User findByid(Long userId);

    boolean existsByusername(String userId);

    boolean existsBynickname(String nickName);


    //Optional<User> findOneWithAuthoritiesByEmail(String username);
}
