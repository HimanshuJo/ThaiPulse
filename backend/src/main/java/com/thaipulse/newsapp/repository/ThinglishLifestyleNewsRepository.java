package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ThinglishLifestyleNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThinglishLifestyleNewsRepository extends JpaRepository<ThinglishLifestyleNews, Long> {
    Page<ThinglishLifestyleNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
