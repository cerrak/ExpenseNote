ALTER TABLE person MODIFY birthday DATE not null;
ALTER TABLE person MODIFY isactive tinyint(1) DEFAULT '0';
ALTER TABLE person MODIFY userrole varchar(255) not null;
ALTER TABLE person MODIFY passwordfield varchar(255) not null;
ALTER TABLE person ADD deleted tinyint(1) DEFAULT '0';

ALTER TABLE expense MODIFY amount varchar(255) not null;
ALTER TABLE expense MODIFY receipt tinyint(1) DEFAULT '0';
ALTER TABLE expense MODIFY expense_date DATE not null;

ALTER TABLE expense_note MODIFY en_date DATE not null;

ALTER TABLE person DROP COLUMN entity;
ALTER TABLE person DROP COLUMN department;
ALTER TABLE person DROP COLUMN supervisor;
ALTER TABLE person DROP COLUMN controller;

ALTER TABLE expense DROP expensecategory;
ALTER TABLE expense ADD expensecategory_id varchar(255) not null;

-- 
create table country (
	id int not null AUTO_INCREMENT,
    name varchar(255) not null,
    constraint pk_country_id primary key(id),
    constraint unique_country_name UNIQUE (name)
);

INSERT INTO country (name) VALUES ('Belgique');
ALTER TABLE expense DROP COLUMN country;
ALTER TABLE expense ADD country_id int not null  DEFAULT '1';