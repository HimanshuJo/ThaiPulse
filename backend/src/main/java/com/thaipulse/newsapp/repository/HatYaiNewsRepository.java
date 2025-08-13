package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.HatYaiNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HatYaiNewsRepository extends JpaRepository<HatYaiNews, Long> {
    Page<HatYaiNews> findAll(Pageable pageable);

    boolean existsByLink(String link);
}
