drop database if exists htdbankdb;

create database htdbankdb;

use htdbankdb;

create table Customer
(
	customer_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    ssn int not null
);

create table Bank
(
	bank_id int primary key auto_increment,
    routing_number int not null
);

create table `Account`
(
	account_id int primary key auto_increment,
    customer_id int not null,
    bank_id int not null,
    account_balance decimal(10,2) not null,
    constraint fk_account_customer_id
		foreign key (customer_id)
        references Customer(customer_id),
	constraint fk_account_bank_id
		foreign key (bank_id)
        references Bank(bank_id)
);

create table Card
(
	card_id int primary key auto_increment,
    `type` varchar(10) not null,
    account_id int not null,
    customer_id int not null,
    constraint fk_card_account_id
		foreign key (account_id)
        references `Account`(account_id),
	constraint fk_card_customer_id
		foreign key (customer_id)
        references Customer(customer_id)
);

create table Employee
(
	employee_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    salary decimal(6,2) not null,
    bank_id int not null,
    constraint fk_employee_bank_id
		foreign key (bank_id)
        references Bank(bank_id)
);

create table `Transaction`
(
	transaction_id int primary key auto_increment,
    `action` varchar(10) not null,
    amount decimal(10,2) not null
);

