create table if not exists User (
	user_Id identity
	email varchar(100) not null,
	password varchar(255) not null,
	api_Key varchar(255) not null
	
);

create table if not exists Todo (
	todo_Id identity,
	todo_Title varchar(255) not null,
	todo_Desc varchar(255),
	todo_Status varchar(30),
	owner_Id bigint not null
);

alter table Todo add foreign key (owner_Id) references User(user_Id);