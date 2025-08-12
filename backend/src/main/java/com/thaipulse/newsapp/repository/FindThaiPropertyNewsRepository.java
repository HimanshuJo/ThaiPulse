package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.FindThaiPropertyNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FindThaiPropertyNewsRepository extends JpaRepository<FindThaiPropertyNews, Long> {
    Page<FindThaiPropertyNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
