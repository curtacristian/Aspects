create table Seat(
id int primary key,
place int,
price int,
available varchar(20),
position varchar(20)
)

create table Show(
id int primary key,
showdate varchar(20),
showtime varchar(20),
name varchar(20),
totalplaces int,
availableplaces int,
)

create table ShowSeat(
showid int foreign key references Show(id),
seatid int foreign key references Seat(id),
primary key(showid,seatid)
)

create table Ticket(
id int primary key,
show int,
showseat int,
foreign key (show,showseat) references ShowSeat(showid,seatid),
)

create table Customer(
id int primary key,
name varchar(20),
passwd varchar(20),
addr varchar(50),
)

create table CustomerTicket(
customerid int foreign key references Customer(id),
ticketid int foreign key references Ticket(id),
primary key(ticketid)
)

create table Administrator(
aid int primary key,
name varchar(20),
pass varchar(20)
)

drop table Administrator

drop table Ticket
drop table ShowSeat
drop table Seat
drop table Show

drop table CustomerTicket

drop table Customer

