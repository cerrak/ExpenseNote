create table person (
	id int not null AUTO_INCREMENT,
	firstName varchar(255) not null,
	lastName varchar(255) not null,
	street varchar(255),
	postalCode numeric(6),
	city varchar(255),
	birthday DATE not null,
	mobile varchar(255),
	email varchar(255) not null,
	title varchar(255),
	isactive tinyint(1) DEFAULT '0',
	userrole varchar(255) not null,
	passwordfield varchar(255) not null,
	deleted tinyint(1) DEFAULT '0',
	department_id int not null,
	constraint pk_personid primary key (id),
	constraint fk_person_department FOREIGN KEY (department_id) references department(id),
	constraint uc_email UNIQUE (email)
);

create table expense (
	id int not null AUTO_INCREMENT,
	supplier varchar(255),
	city varchar(255),
	amount varchar(255) not null,
	receipt tinyint(1) DEFAULT '0',
	expense_date DATE not null,
	comment varchar(255),
    person_id int not null,
    currency varchar(255),
    expensecategory_id int not null,
    expense_note_id int,
    country_id int not null,
	constraint pk_ExpenseID primary key (id),
	constraint fk_expense_category FOREIGN KEY (expensecategory_id) references category(id),
    constraint fk_expense_country FOREIGN KEY (country_id) references country(id),
	constraint fk_expense_person FOREIGN KEY (person_id) references person(id),
	constraint fk_expense_note foreign key (expense_note_id) references expense_note(id)

);

create table expense_note (
	id int not null AUTO_INCREMENT,
    en_name varchar(255) not null,
    en_date DATE not null,
    comment varchar(255),
    person_id int not null,
    en_status varchar(255) not null,
    constraint fk_expense_note_person FOREIGN KEY (person_id) references person(id),
    constraint pk_expense_note_ID primary key(id)


);

create table country (
	id int not null AUTO_INCREMENT,
    name varchar(255) not null,
    constraint pk_country_id primary key(id),
    constraint unique_country_name UNIQUE (name)
);

create table entity (
	id int not null AUTO_INCREMENT,
    name varchar(255) not null,
    city varchar(255) not null,
    locality varchar(255) not null,
    country_id int not null,
    deleted tinyint(1) DEFAULT '0',
    constraint fk_entity_country FOREIGN KEY (country_id) references country(id),
    constraint pk_entity_id primary key(id),
    constraint unique_entity_name UNIQUE (name)
);

create table department (
	id int not null AUTO_INCREMENT,
    name varchar(255) not null,
    entity_id int not null,
    deleted tinyint(1) DEFAULT '0',
    constraint fk_department_entity FOREIGN KEY (entity_id) references entity(id),
    constraint pk_department_id primary key(id),
    constraint unique_department_name UNIQUE (name)
);


create table controller (
	person_id int not null,
	entity_id int not null,
    constraint fk_controller_entity FOREIGN KEY (entity_id) references entity(id),
	constraint fk_supervisor_person FOREIGN KEY (person_id) references person(id)
);

create table category (
	id int not null AUTO_INCREMENT,
    name varchar(255) not null,
    constraint pk_category_id primary key(id),
    constraint unique_category_name UNIQUE (name)
);



