package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ThaiLawyersNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaiLawyersNewsRepository extends JpaRepository<ThaiLawyersNews, Long> {
    Page<ThaiLawyersNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
