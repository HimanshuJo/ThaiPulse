package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ThaifoodmasterNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThaifoodmasterNewsRepository extends JpaRepository<ThaifoodmasterNews, Long> {
    Page<ThaifoodmasterNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
