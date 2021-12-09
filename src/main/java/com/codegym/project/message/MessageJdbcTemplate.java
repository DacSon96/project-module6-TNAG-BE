package com.codegym.project.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageJdbcTemplate {
    @Autowired
    private JdbcTemplate jdbcTemplate;


}
