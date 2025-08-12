package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ThaiLadyDateFinderNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaiLadyDateFinderNewsRepository extends JpaRepository<ThaiLadyDateFinderNews, Long> {
    Page<ThaiLadyDateFinderNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
