package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.FashionGalleriaNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FashionGalleriaNewsRepository extends JpaRepository<FashionGalleriaNews, Long> {
    Page<FashionGalleriaNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
