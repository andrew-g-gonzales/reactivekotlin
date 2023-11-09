DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name VARCHAR(250) NOT NULL,
    email VARCHAR(250) NOT NULL
);

INSERT INTO users (first_name, last_name, email) VALUES
('Andrew01', 'Gonzales01', 'andgonz01@test.com'),
('Andrew02', 'Gonzales02', 'andgonz02@test.com'),
('Andrew03', 'Gonzales03', 'andgonz03@test.com'),
('Andrew04', 'Gonzales04', 'andgonz04@test.com'),
('Andrew05', 'Gonzales05', 'andgonz05@test.com'),
('Andrew06', 'Gonzales06', 'andgonz07@test.com'),
('Andrew07', 'Gonzales07', 'andgonz07@test.com'),
('Andrew08', 'Gonzales08', 'andgonz08@test.com'),
('Andrew09', 'Gonzales09', 'andgonz09@test.com'),
('Andrew10', 'Gonzales10', 'andgonz10@test.com'),
('Andrew11', 'Gonzales11', 'andgonz11@test.com'),
('Andrew12', 'Gonzales12', 'andgonz12@test.com');
