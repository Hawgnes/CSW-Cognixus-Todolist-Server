create table if not exists Todo (
	todo_Id bigint not null auto_increment,
	todo_Title varchar(255) not null,
	todo_Desc varchar(255),
	todo_Status varchar(30) not null,
	owner_Id varchar(100) not null,
    created_At date not null,
	primary key (todo_Id)
);

