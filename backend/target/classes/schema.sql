-- Barbershop Database Schema
-- This script creates the necessary tables for the barbershop appointment system

-- Create customers table
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    birth_date DATE,
    preferred_services TEXT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create business_owners table
CREATE TABLE IF NOT EXISTS business_owners (
    id BIGSERIAL PRIMARY KEY,
    business_name VARCHAR(100) NOT NULL,
    owner_name VARCHAR(50) NOT NULL,
    owner_surname VARCHAR(50) NOT NULL,
    phone VARCHAR(11) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    address TEXT NOT NULL,
    city VARCHAR(50) NOT NULL,
    district VARCHAR(50) NOT NULL,
    postal_code VARCHAR(5),
    tax_number VARCHAR(11),
    services TEXT,
    working_hours TEXT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create appointments table
CREATE TABLE IF NOT EXISTS appointments (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    business_owner_id BIGINT NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    services TEXT NOT NULL,
    total_price DECIMAL(10,2),
    notes TEXT,
    status VARCHAR(20) DEFAULT 'PENDING',
    notification_sent BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE,
    FOREIGN KEY (business_owner_id) REFERENCES business_owners(id) ON DELETE CASCADE
);

-- Create notifications table
CREATE TABLE IF NOT EXISTS notifications (
    id BIGSERIAL PRIMARY KEY,
    business_owner_id BIGINT NOT NULL,
    appointment_id BIGINT,
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    type VARCHAR(50) DEFAULT 'APPOINTMENT',
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP,
    FOREIGN KEY (business_owner_id) REFERENCES business_owners(id) ON DELETE CASCADE,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id) ON DELETE SET NULL
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_customers_phone ON customers(phone);
CREATE INDEX IF NOT EXISTS idx_customers_email ON customers(email);
CREATE INDEX IF NOT EXISTS idx_customers_created_at ON customers(created_at);

CREATE INDEX IF NOT EXISTS idx_business_owners_phone ON business_owners(phone);
CREATE INDEX IF NOT EXISTS idx_business_owners_email ON business_owners(email);
CREATE INDEX IF NOT EXISTS idx_business_owners_city ON business_owners(city);
CREATE INDEX IF NOT EXISTS idx_business_owners_created_at ON business_owners(created_at);

CREATE INDEX IF NOT EXISTS idx_appointments_customer_id ON appointments(customer_id);
CREATE INDEX IF NOT EXISTS idx_appointments_business_owner_id ON appointments(business_owner_id);
CREATE INDEX IF NOT EXISTS idx_appointments_date ON appointments(appointment_date);
CREATE INDEX IF NOT EXISTS idx_appointments_status ON appointments(status);
CREATE INDEX IF NOT EXISTS idx_appointments_created_at ON appointments(created_at);

CREATE INDEX IF NOT EXISTS idx_notifications_business_owner_id ON notifications(business_owner_id);
CREATE INDEX IF NOT EXISTS idx_notifications_appointment_id ON notifications(appointment_id);
CREATE INDEX IF NOT EXISTS idx_notifications_is_read ON notifications(is_read);
CREATE INDEX IF NOT EXISTS idx_notifications_type ON notifications(type);
CREATE INDEX IF NOT EXISTS idx_notifications_created_at ON notifications(created_at);

-- Add constraints for appointment status
ALTER TABLE appointments ADD CONSTRAINT chk_appointment_status 
    CHECK (status IN ('PENDING', 'CONFIRMED', 'COMPLETED', 'CANCELLED'));

-- Add constraints for notification type
ALTER TABLE notifications ADD CONSTRAINT chk_notification_type 
    CHECK (type IN ('APPOINTMENT', 'SYSTEM', 'REMINDER', 'CANCELLATION', 'UPDATE'));

-- Add trigger to update updated_at column automatically
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create triggers for updated_at columns
CREATE TRIGGER update_customers_updated_at BEFORE UPDATE ON customers
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_business_owners_updated_at BEFORE UPDATE ON business_owners
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_appointments_updated_at BEFORE UPDATE ON appointments
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();