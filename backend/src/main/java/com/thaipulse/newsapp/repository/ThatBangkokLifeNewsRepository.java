package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ThatBangkokLifeNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThatBangkokLifeNewsRepository extends JpaRepository<ThatBangkokLifeNews, Long> {
    Page<ThatBangkokLifeNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
