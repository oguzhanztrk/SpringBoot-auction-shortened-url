package com.noins.auctionshortenedurl.repository;

import com.noins.auctionshortenedurl.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFindRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);

}
