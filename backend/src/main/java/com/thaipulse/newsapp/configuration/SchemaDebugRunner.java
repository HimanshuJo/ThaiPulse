package com.thaipulse.newsapp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class SchemaDebugRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(SchemaDebugRunner.class);
    private final JdbcTemplate jdbcTemplate;

    public SchemaDebugRunner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        log.info("=== DB Tables After Schema Init ===");
        jdbcTemplate.queryForList("SHOW TABLES").forEach(row -> log.info(row.toString()));
    }
}
