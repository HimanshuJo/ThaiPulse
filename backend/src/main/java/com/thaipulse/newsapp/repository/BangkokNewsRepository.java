package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.BangkokNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BangkokNewsRepository extends JpaRepository<BangkokNews, Long> {
    Page<BangkokNews> findAll(Pageable pageable);

    boolean existsByLink(String link);
}
