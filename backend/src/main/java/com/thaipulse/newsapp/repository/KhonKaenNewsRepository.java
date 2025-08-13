package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.KhonKaenNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhonKaenNewsRepository extends JpaRepository<KhonKaenNews, Long> {
    Page<KhonKaenNews> findAll(Pageable pageable);

    boolean existsByLink(String link);
}
