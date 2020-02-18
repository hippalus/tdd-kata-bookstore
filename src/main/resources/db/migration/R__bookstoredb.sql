create sequence hibernate_sequence start with 1 increment by 1;
create table book (id binary(255) not null, name binary(255), price binary(255), category_id binary(255), primary key (id));
create table book_bookstores (book_id binary(255) not null, bookstores binary(255) not null, primary key (book_id, bookstores));
create table book_category (id binary(255) not null, name binary(255), primary key (id));
create table book_registration (id bigint not null, book_id binary(255), bookstore_id binary(255), primary key (id));
create table bookstore (id binary(255) not null, city_id binary(255), primary key (id));
create table bookstore_book_items (bookstore_id binary(255) not null, book_items binary(255) not null, primary key (bookstore_id, book_items));
create table city (id binary(255) not null, city_name binary(255), primary key (id));


alter table book add constraint FK5jgwecmfn1vyn9jtld3o64v4x foreign key (category_id) references book_category;
alter table book_bookstores add constraint FKngxvsvhuglw3xq6vh87wxckc5 foreign key (bookstores) references bookstore;
alter table book_bookstores add constraint FKl506rigeedstiy8ugeq7cjlik foreign key (book_id) references book;
alter table book_registration add constraint FK7w4sqdp1k61nbmxutpkubj8fm foreign key (book_id) references book;
alter table book_registration add constraint FKdwgcyrl8v20v6vr1xikbpawrg foreign key (bookstore_id) references bookstore;
alter table bookstore add constraint FKk9kr3euk4e3yr82vcwumacee4 foreign key (city_id) references city;
alter table bookstore_book_items add constraint FK11i74qe8catg4393v5nlbx0hy foreign key (book_items) references book;
alter table bookstore_book_items add constraint FKs03pgxaf121gnhp10gp4hjise foreign key (bookstore_id) references bookstore;