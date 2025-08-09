package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.PhuketNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhuketNewsRepository extends JpaRepository<PhuketNews, Long> {
    Optional<PhuketNews> findByLink(String link);
}
