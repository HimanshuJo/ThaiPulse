package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.LifestyleInThailandNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifestyleInThailandNewsRepository extends JpaRepository<LifestyleInThailandNews, Long> {
    Page<LifestyleInThailandNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
