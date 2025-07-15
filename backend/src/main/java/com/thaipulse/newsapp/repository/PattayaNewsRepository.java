package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.PattayaNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PattayaNewsRepository extends JpaRepository<PattayaNews, Long> {
    Optional<PattayaNews> findByLink(String link);
}
