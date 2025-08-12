package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.LegallyMarriedInThailandNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LegallyMarriedInThailandNewsRepository extends JpaRepository<LegallyMarriedInThailandNews, Long> {
    Page<LegallyMarriedInThailandNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
