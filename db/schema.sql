CREATE TABLE IF NOT EXISTS persons(
	dtype varchar(50) not null,
	id SERIAL PRIMARY KEY not null,
	card bytea,
	type integer,
	hire_time timestamp,
	fired_time timestamp,
	visit_date timestamp,
	department_id integer CONSTRAINT references_department REFERENCES department,
	emp_id integer CONSTRAINT references_employee REFERENCES persons
);

CREATE TABLE IF NOT EXISTS department(
	id SERIAL PRIMARY KEY,
	name varchar(32)
);

INSERT INTO department(id, name) VALUES
(1, 'IT отдел'),
(2, 'Отдел Бухгалтерии'),
(3, 'Отдел Менеджера'),
(4, 'Отдел Экономистов'),
(5, 'Отдел Аналитиков'),
(6, 'Отдел Тестировщиков'),
(7, 'Отдел Администраторов'),
(8, 'Отдел Кадров');