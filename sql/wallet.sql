-- Enable UUID extension for PostgreSQL
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Create schema if it doesn't exist
CREATE SCHEMA IF NOT EXISTS wallet;

-- USER TABLE
CREATE TABLE IF NOT EXISTS wallet.users (
    row_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    balance NUMERIC(19, 4) NOT NULL DEFAULT 0.0,
    version BIGINT NOT NULL DEFAULT 0
);

-- TRANSACTION TABLE
CREATE TABLE IF NOT EXISTS wallet.transactions (
    row_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    transaction_type VARCHAR(20) NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    source_user_id UUID NOT NULL,
    destination_user_id UUID,
    trasaction_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT fk_source_user FOREIGN KEY (source_user_id) REFERENCES wallet.users(row_id),
    CONSTRAINT fk_destination_user FOREIGN KEY (destination_user_id) REFERENCES wallet.users(row_id)
);

-- IDEMPOTENCY RECORD TABLE
CREATE TABLE IF NOT EXISTS wallet.idempotency_record (
    row_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    idempotency_key VARCHAR(255) NOT NULL UNIQUE,
    endpoint VARCHAR(255) NOT NULL,
    created_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    response_payload TEXT NOT NULL
);