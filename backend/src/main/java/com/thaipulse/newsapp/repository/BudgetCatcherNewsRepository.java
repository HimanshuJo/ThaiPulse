package com.thaipulse.newsapp.repository;

import com.thaipulse.newsapp.model.BudgetCatcherNews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetCatcherNewsRepository extends JpaRepository<BudgetCatcherNews, Long> {
    Page<BudgetCatcherNews> findAll(Pageable pageable);
    boolean existsByLink(String link);
}
