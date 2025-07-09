-- Database initialization script
-- This script will be executed when the PostgreSQL container starts for the first time

-- Create database if it doesn't exist (PostgreSQL creates it automatically based on POSTGRES_DB env var)
-- You can add any initial data or schema modifications here if needed

-- Example: Create any additional schemas or extensions if needed
-- CREATE SCHEMA IF NOT EXISTS public;

-- Example: Insert initial data if needed
-- INSERT INTO users (username, email, role) VALUES ('admin', 'admin@example.com', 'ADMIN') ON CONFLICT DO NOTHING;

-- The actual tables will be created by JPA/Hibernate with ddl-auto: update 