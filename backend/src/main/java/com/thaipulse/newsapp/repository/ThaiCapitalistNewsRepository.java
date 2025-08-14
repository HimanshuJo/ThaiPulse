package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ThaiCapitalistNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaiCapitalistNewsRepository extends JpaRepository<ThaiCapitalistNews, Long> {
    Page<ThaiCapitalistNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
