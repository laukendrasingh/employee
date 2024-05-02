CREATE SCHEMA IF NOT EXISTS employee;

USE employee;
CREATE TABLE IF NOT EXISTS employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

INSERT INTO employee (name) VALUES ('John Doe'), ('Jane Smith');

