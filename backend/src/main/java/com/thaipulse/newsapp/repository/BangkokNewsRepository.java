package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.BangkokNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BangkokNewsRepository extends JpaRepository<BangkokNews, Long> {
    Optional<BangkokNews> findByLink(String link);
}
