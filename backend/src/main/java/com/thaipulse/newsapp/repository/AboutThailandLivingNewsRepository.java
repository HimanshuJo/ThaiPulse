package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.AboutThailandLivingNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutThailandLivingNewsRepository extends JpaRepository<AboutThailandLivingNews, Long> {
    Page<AboutThailandLivingNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
