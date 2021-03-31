create table hibernate_sequence (next_val bigint) engine=InnoDB
insert into hibernate_sequence values ( 1 )
create table user (id integer not null, email varchar(100), first_name varchar(100), last_name varchar(100), password varchar(50), phone_number varchar(15), phone_numberr varchar(15), primary key (id)) engine=InnoDB
