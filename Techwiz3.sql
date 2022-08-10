create database Techwiz
go
use Techwiz
go


create table users 
(
	user_id int primary key identity , 
	fullname varchar(200),
	phone_number varchar(200),
	email varchar(200),
	address varchar(200),
	password varchar(200),
	role varchar(200)
)

go

create table student_data
(
	id int primary key,
	user_id int foreign key references users,
	user_name varchar(200),
	class varchar(200),
	enrollment_date datetime,
)

create table parrent_data
(
	id int primary key,
	student_id int foreign key references student_data
)
go
create table teacher_data 
(
	id int primary key identity , 
	user_id int foreign key references users,
	user_name varchar(200),
	subject varchar(200)
)
go
create table class --Seeding data , tạo dữ liệu = SQL , trên code kh có hàm tạo , chỉ có các hàm get dữ liệu
(
	id int primary key identity,
	class_name varchar(100)
)
create table subject --Seeding data , tạo dữ liệu = SQL , trên code kh có hàm tạo , chỉ có các hàm get dữ liệu
(	
	subject_name varchar(100) primary key
)
go
create table test_type --Seeding data , tạo dữ liệu = SQL , trên code kh có hàm tạo , chỉ có các hàm get dữ liệu
(
	id int primary key identity ,
	type_name varchar(200)
)
create table test
(
	id int primary key identity,
	subject_name varchar(100) foreign key references subject,
	type_id int foreign key references test_type
)
go
create table score_details
(
	id int primary key identity , 
	test_id int foreign key references test,
	description varchar(100),
	date datetime,
	score float ,
	student_id int foreign key references student_data
)
go
create table study_resource
(
	id int primary key identity,
	content varchar(255)
)

create table review_class
(
	id int primary key identity , 
	class_id int foreign key references class,
	content varchar(255),
	date_start datetime
)
create table contact
(
	id int primary key identity, 
	sent_by varchar(200),
	sent_date datetime,
	content varchar(max)
)
