create table if not exists User (
	user_Id bigint not null auto_increment,
	email varchar(100) not null,
	password varchar(255) not null,
	api_Key varchar(255) not null,
    created_At date not null,
    primary key (user_Id)
);

create table if not exists Todo (
	todo_Id bigint not null auto_increment,
	todo_Title varchar(255) not null,
	todo_Desc varchar(255),
	todo_Status varchar(30),
    created_At date not null,
	owner_Id bigint not null,
	primary key (todo_Id)
);

alter table Todo add foreign key (owner_Id) references User(user_Id);

# sets the auto_increment starting values
alter table Todo auto_increment=50000;
alter table User auto_increment=10000000;