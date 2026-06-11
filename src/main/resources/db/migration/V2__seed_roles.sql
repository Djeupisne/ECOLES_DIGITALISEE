-- Seed a default admin user (password is 'admin123' encoded with BCrypt)
INSERT INTO users (username, password, email, role, enabled)
VALUES ('admin', '$2a$12$UMEmE2ogJqGm0kaxHbD9y.TggZxLWhYJau3pZUJhTZ7NpO/vMXQRG', 'admin@school.com', 'ADMIN', true);