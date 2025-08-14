package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.DaveTheRavesThailandNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DaveTheRavesThailandNewsRepository extends JpaRepository<DaveTheRavesThailandNews, Long> {
    Page<DaveTheRavesThailandNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
