drop database if exists htdbankdb_test;

create database htdbankdb_test;

use htdbankdb_test;

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

delimiter //
create procedure set_known_good_state()
begin

    delete from `Account`;
    alter table `Account` auto_increment = 1;
    delete from Employee;
    alter table Employee auto_increment = 1;
    delete from Card;
    alter table Card auto_increment = 1;
    delete from Customer;
    alter table Customer auto_increment = 1;
    delete from Bank;
    alter table Bank auto_increment = 1;
    delete from `Transaction`;
    alter table `Transaction` auto_increment = 1;
	
    insert into Customer(customer_id, first_name, last_name, ssn) values
		(1, 'Giblert', 'Keys', 123456789),
        (2, 'Temuudei', 'Shiilegdamba', 123456789);
        
	insert into Bank(bank_id, routing_number) values
		(1, 999999999),
        (2, 999999999);
	
    insert into `Account`(account_id, customer_id, bank_id, account_balance) values
		(1, 1, 1, 547.34),
        (2, 2, 2, 34367.23);
	
    insert into Card(card_id, `type`, account_id, customer_id) values
		(1, 'Credit', 1, 1),
        (2, 'Debit', 2, 2);
	
    insert into Employee(employee_id, first_name, last_name, salary, bank_id) values
		(1, 'John', 'Whick', 348.23, 1),
        (2, 'Lebron', 'James', 322.56, 2);
	
    insert into `Transaction`(transaction_type, amount) values
		('Deposit', '23231.56'),
        ('Withdraw', 65.46);
	
end //
delimiter ;
