create table person (
	id INTEGER PRIMARY KEY,
	first_name VARCHAR(50),
	last_name VARCHAR(50),
	country VARCHAR(50),
	timezone VARCHAR(50),
    last_update TIMESTAMP WITH TIME ZONE NOT NULL
);