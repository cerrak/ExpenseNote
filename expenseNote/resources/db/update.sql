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

--

ALTER TABLE expense ADD CONSTRAINT fk_expense_country FOREIGN KEY (country_id) references country(id) ON DELETE SET NULL;;
ALTER TABLE expense MODIFY country_id int not null;

create table category (
	id int not null AUTO_INCREMENT,
    name varchar(255) not null,
    constraint pk_category_id primary key(id),
    constraint unique_category_name UNIQUE (name)
);

INSERT INTO category(name) VALUES ('Restaurant');
ALTER TABLE expense DROP expensecategory_id;
ALTER TABLE expense ADD expensecategory_id int not null  DEFAULT '1';
ALTER TABLE expense MODIFY expensecategory_id int not null;

ALTER TABLE expense ADD CONSTRAINT fk_expense_category FOREIGN KEY (expensecategory_id) references category(id) ON DELETE SET NULL;