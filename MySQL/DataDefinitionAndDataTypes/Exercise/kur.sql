DROP DATABASE IF EXISTS SoftUni;
CREATE DATABASE SoftUni;
USE SoftUni;

DROP TABLE IF EXISTS towns;
CREATE TABLE towns(
    name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS departments;
CREATE TABLE departments(
    name VARCHAR(50) NOT NULL
);

DROP TABLE IF EXISTS employees;
CREATE TABLE employees(
    name VARCHAR(50) NOT NULL,
    job_title VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    hire_date DATE NOT NULL,
    salary FLOAT(2)
);

INSERT INTO towns
VALUES
(1, "Sofia"),
(2, "Plovdiv"),
(3, "Varna"),
(4, "Burgas");

INSERT INTO departments
VALUES
(1, "Engineering"), 
(2, "Sales"), 
(3, "Marketing"), 
(4, "Software Development"), 
(5, "Quality Assurance");

INSERT INTO employees
VALUES
(1, "Ivan Ivanov Ivanov", ".NET Developer", "Software Development", "01/02/2013", 3500.00),
(2, "Petar Petrov Petrov", "Senior Engineer", "Engineering", "02/03/2004", 4000.00),
(3, "Maria Petrova Ivanova", "Intern", "Quality Assurance", "28/08/2016", 525.25),
(4, "Georgi Terziev Ivanov", "CEO", "Sales", "09/12/2007", 3000.00),
(5, "Peter Pan Pan", "Intern", "Marketing", "28/08/2016", 599.88);

