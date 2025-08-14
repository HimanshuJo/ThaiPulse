package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.TheSilomerNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheSilomerNewsRepository extends JpaRepository<TheSilomerNews, Long> {
    Page<TheSilomerNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
