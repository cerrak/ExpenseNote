ALTER TABLE person MODIFY birthday DATE not null;
ALTER TABLE person MODIFY isactive tinyint(1) DEFAULT '0';
ALTER TABLE person MODIFY userrole varchar(255) not null;
ALTER TABLE person MODIFY passwordfield varchar(255) not null;
ALTER TABLE person ADD deleted tinyint(1) DEFAULT '0';

ALTER TABLE expense MODIFY amount varchar(255) not null;
ALTER TABLE expense MODIFY receipt tinyint(1) DEFAULT '0';
ALTER TABLE expense MODIFY expense_date DATE not null;

ALTER TABLE expense_note MODIFY en_date DATE not null;