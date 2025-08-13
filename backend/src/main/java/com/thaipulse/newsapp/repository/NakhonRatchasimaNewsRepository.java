package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.NakhonRatchasimaNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NakhonRatchasimaNewsRepository extends JpaRepository<NakhonRatchasimaNews, Long> {
    Page<NakhonRatchasimaNews> findAll(Pageable pageable);

    boolean existsByLink(String link);
}
