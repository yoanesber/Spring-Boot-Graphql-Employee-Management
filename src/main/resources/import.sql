-- This file is used to import data into the database.
--
-- Data for Name: employee;
--

INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(1, '1990-07-08', 'Jenny', 'Isabelle', 'F', '2025-01-01', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(2, '1964-06-02', 'Bezalel', 'Simmel', 'F', '1985-11-21', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(3, '1954-05-01', 'Chirstian', 'Koblick', 'M', '1986-12-01', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(4, '1955-01-21', 'Kyoichi', 'Maliniak', 'M', '1989-09-12', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(5, '1953-04-20', 'Anneke', 'Preusig', 'F', '1989-06-02', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(6, '1957-05-23', 'Tzvetan', 'Zielinski', 'F', '1989-02-10', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(7, '1958-02-19', 'Saniya', 'Kalloufi', 'M', '1994-09-15', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(8, '1953-11-07', 'Mary', 'Sluis', 'F', '1990-01-22', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(9, '1962-07-10', 'Divier', 'Reistad', 'F', '1989-07-07', true, false, 1);
INSERT INTO employee (id, birth_date, first_name, last_name, gender, hire_date, active, is_deleted, created_by) VALUES(10, '1956-12-13', 'Otmar', 'Herbst', 'M', '1985-11-20', true, false, 1);

--
-- Data for Name: department;
--

INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d001', 'Marketing', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d002', 'Finance', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d003', 'Human Resources', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d004', 'Production', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d005', 'Development', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d006', 'Quality Management', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d007', 'Sales', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d008', 'Research', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d009', 'Customer Service', true, false, 1);
INSERT INTO department (id, dept_name, active, is_deleted, created_by) VALUES('d010', 'Information Technology', true, false, 1);

--
-- Data for Name: department_employee;
--

INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(1, 'd002', '2025-01-01', '2025-03-30');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(2, 'd010', '2024-01-01', '2024-09-30');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(3, 'd003', '1986-12-01', '9999-01-01');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(4, 'd003', '1989-09-12', '9999-01-01');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(5, 'd005', '1990-08-05', '9999-01-01');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(6, 'd008', '1989-02-10', '9999-01-01');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(7, 'd005', '1998-03-11', '2000-07-31');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(8, 'd009', '1990-01-22', '1996-11-09');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(9, 'd005', '1995-04-02', '9999-01-01');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(10, 'd004', '1991-09-18', '1999-07-08');
INSERT INTO department_employee (employee_id, department_id, from_date, to_date) VALUES(10, 'd006', '1999-07-08', '9999-01-01');

--
-- Data for Name: salary;
--

INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(1, 60117, '2000-01-01', '2005-12-31');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(2, 65828, '1996-08-03', '1997-08-03');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(2, 65909, '1997-08-03', '1998-08-03');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(2, 67534, '1998-08-03', '1999-08-03');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(2, 69366, '1999-08-03', '2000-08-02');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(2, 71963, '2000-08-02', '2001-08-02');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(2, 72527, '2001-08-02', '9999-01-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 40054, '1986-12-01', '1987-12-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 42283, '1987-12-01', '1988-11-30');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 42542, '1988-11-30', '1989-11-30');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 46065, '1989-11-30', '1990-11-30');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 48271, '1990-11-30', '1991-11-30');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 50594, '1991-11-30', '1992-11-29');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 52119, '1992-11-29', '1993-11-29');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 54693, '1993-11-29', '1994-11-29');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 58326, '1994-11-29', '1995-11-29');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 60770, '1995-11-29', '1996-11-28');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 62566, '1996-11-28', '1997-11-28');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 64340, '1997-11-28', '1998-11-28');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 67096, '1998-11-28', '1999-11-28');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 69722, '1999-11-28', '2000-11-27');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 70698, '2000-11-27', '2001-11-27');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(3, 74057, '2001-11-27', '9999-01-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 78228, '1989-09-12', '1990-09-12');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 82621, '1990-09-12', '1991-09-12');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 83735, '1991-09-12', '1992-09-11');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 85076, '1993-09-11', '1994-09-11');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 85572, '1992-09-11', '1993-09-11');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 86050, '1994-09-11', '1995-09-11');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 88063, '1996-09-10', '1997-09-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 88448, '1995-09-11', '1996-09-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 89724, '1997-09-10', '1998-09-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 90392, '1998-09-10', '1999-09-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 90531, '1999-09-10', '2000-09-09');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 91453, '2000-09-09', '2001-09-09');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(4, 94692, '2001-09-09', '9999-01-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 40000, '1990-08-05', '1991-08-05');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 42085, '1991-08-05', '1992-08-04');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 42629, '1992-08-04', '1993-08-04');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 45844, '1993-08-04', '1994-08-04');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 47518, '1994-08-04', '1995-08-04');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 47917, '1995-08-04', '1996-08-03');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 52255, '1996-08-03', '1997-08-03');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 53747, '1997-08-03', '1998-08-03');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 56032, '1998-08-03', '1999-08-03');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 58299, '1999-08-03', '2000-08-02');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 59755, '2001-08-02', '9999-01-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(5, 60098, '2000-08-02', '2001-08-02');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 56724, '1989-02-10', '1990-02-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 60740, '1990-02-10', '1991-02-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 62745, '1991-02-10', '1992-02-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 63208, '1993-02-09', '1994-02-09');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 63475, '1992-02-10', '1993-02-09');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 64563, '1994-02-09', '1995-02-09');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 68833, '1995-02-09', '1996-02-09');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 70220, '1996-02-09', '1997-02-08');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 73362, '1997-02-08', '1998-02-08');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 75582, '1998-02-08', '1999-02-08');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 79513, '1999-02-08', '2000-02-08');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 80083, '2000-02-08', '2001-02-07');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 84456, '2001-02-07', '2002-02-07');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(6, 88070, '2002-02-07', '9999-01-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(7, 46671, '1998-03-11', '1999-03-11');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(7, 48584, '1999-03-11', '2000-03-10');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(7, 52668, '2000-03-10', '2000-07-31');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(8, 42365, '1990-01-22', '1991-01-22');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(8, 44200, '1991-01-22', '1992-01-22');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(8, 48214, '1992-01-22', '1993-01-21');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(8, 50927, '1993-01-21', '1994-01-21');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(8, 51470, '1994-01-21', '1995-01-21');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(8, 54545, '1995-01-21', '1996-01-21');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(8, 56753, '1996-01-21', '1996-11-09');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 39520, '1996-04-01', '1997-04-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 40000, '1995-04-02', '1996-04-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 42382, '1997-04-01', '1998-04-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 43654, '1998-04-01', '1999-04-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 43925, '1999-04-01', '2000-03-31');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 45157, '2000-03-31', '2001-03-31');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 45771, '2001-03-31', '2002-03-31');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(9, 46145, '2002-03-31', '9999-01-01');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 63163, '1991-09-18', '1992-09-17');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 66877, '1992-09-17', '1993-09-17');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 69211, '1993-09-17', '1994-09-17');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 70294, '1995-09-17', '1996-09-16');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 70624, '1994-09-17', '1995-09-17');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 70671, '1996-09-16', '1997-09-16');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 73510, '1997-09-16', '1998-09-16');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 75773, '1998-09-16', '1999-09-16');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 76067, '1999-09-16', '2000-09-15');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 76608, '2000-09-15', '2001-09-15');
INSERT INTO salary (employee_id, amount, from_date, to_date) VALUES(10, 77777, '2001-09-15', '9999-01-01');

--
-- Data for Name: title;
--
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(1, 'Senior Engineer2', '2000-01-01', '2005-12-31');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(2, 'Staff', '1996-08-03', '9999-01-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(3, 'Engineer', '1986-12-01', '1995-12-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(3, 'Senior Engineer', '1995-12-01', '9999-01-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(4, 'Senior Staff', '1996-09-12', '9999-01-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(4, 'Staff', '1989-09-12', '1996-09-12');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(5, 'Senior Engineer', '1990-08-05', '9999-01-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(6, 'Senior Staff', '1996-02-11', '9999-01-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(6, 'Staff', '1989-02-10', '1996-02-11');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(7, 'Assistant Engineer', '1998-03-11', '2000-07-31');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(8, 'Staff', '1990-01-22', '1996-11-09');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(9, 'Engineer', '1995-04-02', '2001-04-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(9, 'Senior Engineer', '2001-04-01', '9999-01-01');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(10, 'Engineer', '1991-09-18', '2000-09-17');
INSERT INTO title (employee_id, title, from_date, to_date) VALUES(10, 'Senior Engineer', '2000-09-17', '9999-01-01');


--
-- Name: employee_id_seq; This belongs to table employee
--

SELECT pg_catalog.setval('employee_id_seq', 10, true);
