package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.PattayaPINews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PattayaPINewsRepository extends JpaRepository<PattayaPINews, Long> {
    Page<PattayaPINews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
