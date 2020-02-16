create sequence hibernate_sequence start with 1 increment by 1;
create table book (id binary(255) not null, name binary(255), price binary(255), category_id binary(255), primary key (id));
create table book_category (id binary(255) not null, book_items binary(255), name binary(255), primary key (id));
create table book_registration (id bigint not null, book_id binary(255), bookstore_id binary(255), primary key (id));
create table bookstore (id binary(255) not null, book_items binary(255), city binary(255), primary key (id));
create table city (id binary(255) not null, city_name binary(255), primary key (id));
alter table book add constraint FK5jgwecmfn1vyn9jtld3o64v4x foreign key (category_id) references book_category;
alter table book_registration add constraint FK7w4sqdp1k61nbmxutpkubj8fm foreign key (book_id) references book;
alter table book_registration add constraint FKdwgcyrl8v20v6vr1xikbpawrg foreign key (bookstore_id) references bookstore;
alter table bookstore add constraint FKj1xw0n86esxp05aw8y829vimi foreign key (city) references city;
 