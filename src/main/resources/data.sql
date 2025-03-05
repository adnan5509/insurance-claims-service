-- Insert users
INSERT INTO users (username, password, enabled) VALUES
('adnan', 'adnan123', TRUE),
('zeshan', 'zeshan123', TRUE),
('kamran', 'kamran123', TRUE),
('noor', 'noor123', TRUE);

-- Assign roles (each user gets either ROLE_CUSTOMER_MANAGER or ROLE_CLAIM_MANAGER)
INSERT INTO authorities (username, authority) VALUES
('adnan', 'ROLE_CUSTOMER_MANAGER'),
('zeshan', 'ROLE_CLAIM_MANAGER'),
('kamran', 'ROLE_CUSTOMER_MANAGER'),
('noor', 'ROLE_CLAIM_MANAGER');
