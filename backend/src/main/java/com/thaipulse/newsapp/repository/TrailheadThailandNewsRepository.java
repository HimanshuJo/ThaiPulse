package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.TrailheadThailandNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailheadThailandNewsRepository extends JpaRepository<TrailheadThailandNews, Long> {
    Page<TrailheadThailandNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
