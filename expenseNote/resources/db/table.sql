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
	constraint pk_personid primary key (id),
	CONSTRAINT uc_email UNIQUE (email)
);

Create table expense (
	id int not null AUTO_INCREMENT,
	supplier varchar(255),
	city varchar(255),
	amount varchar(255) not null,
	receipt tinyint(1) DEFAULT '0',
	expense_date DATE not null,
	comment varchar(255),
	country varchar(255),
    person_id int not null,
    currency varchar(255),
    expensecategory varchar(255) not null,
    expense_note_id int,
	constraint pk_ExpenseID primary key (id),
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
