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
    transaction_type varchar(10) not null,
    amount decimal(10,2) not null
);

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    disabled bit not null default(0)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

