package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.WeddingBoutiquePhuketNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeddingBoutiquePhuketNewsRepository extends JpaRepository<WeddingBoutiquePhuketNews, Long> {
    Page<WeddingBoutiquePhuketNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
