CREATE TABLE employees (
    id INT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    birth_year INT,
    salary INT
);


INSERT INTO employees (id, first_name, last_name, birth_year, salary) VALUES
(1, 'John', 'Doe', 1985, 55000),
(2, 'Jane', 'Smith', 1990, 62000),
(3, 'Michael', 'Johnson', 1982, 75000),
(4, 'Emily', 'Davis', 1995, 47000),
(5, 'David', 'Martinez', 1988, 53000);
