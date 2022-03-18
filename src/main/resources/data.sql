create table person

(

id integer not null,
name varchar(255) not null,
location varchar(255),
birth_date timestamp,
primary key(id)

);

insert into person values(1,'joel','frenkendorf',sysdate());

insert into person values(2,'anita','bärschwil',sysdate());