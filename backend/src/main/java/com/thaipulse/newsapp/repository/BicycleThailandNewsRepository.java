package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.BicycleThailandNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BicycleThailandNewsRepository extends JpaRepository<BicycleThailandNews, Long> {
    Page<BicycleThailandNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
