package com.springboot.repositories;

import com.springboot.entity.ShortUrl;
import com.springboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<ShortUrl,Long> {

    Optional<ShortUrl> findByShortUrlAndUser(String s,User user);
    Optional<ShortUrl> findByShortUrl(String s);
    List<ShortUrl> findAllByUser(User user);

}
