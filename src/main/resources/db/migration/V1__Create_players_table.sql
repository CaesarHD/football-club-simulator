-- ===========================================
--  Flyway Migration Script: V1__Create_players_table.sql
--  Creates database (if not exists) and table players
-- ===========================================

-- Create the database if it doesn't exist
CREATE DATABASE IF NOT EXISTS football_simulator;

-- Select the database
USE football_simulator;

-- Create table players
CREATE TABLE IF NOT EXISTS players (
   id BIGINT NOT NULL AUTO_INCREMENT,
   name VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
    );
