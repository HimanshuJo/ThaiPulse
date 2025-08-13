package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.ChiangMaiNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiangMaiNewsRepository extends JpaRepository<ChiangMaiNews, Long> {
    Page<ChiangMaiNews> findAll(Pageable pageable);

    boolean existsByLink(String link);
}
