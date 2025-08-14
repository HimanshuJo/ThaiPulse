package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ThailandBailNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThailandBailNewsRepository extends JpaRepository<ThailandBailNews, Long> {
    Page<ThailandBailNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
