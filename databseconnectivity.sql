create database onlinetravellingsystem;

show databases;

use onlinetravellingsystem;

create table account(username varchar(20) PRIMARY KEY, name varchar(20), password varchar(20), security varchar(100), answer varchar(50));

show tables;

select * from account;

ALTER TABLE account MODIFY COLUMN password VARCHAR(256);

create table customer(username varchar(20), id varchar(30), number varchar(30), name varchar(30), gender varchar (20), country varchar(20), address varchar (80), email varchar(50) PRIMARY KEY, phone varchar(20));

select * from customer; 

CREATE TABLE date (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    fromdest VARCHAR(30),
    todest VARCHAR(30),
    PRIMARY KEY (id),
    FOREIGN KEY (username) REFERENCES account(username)
);

select * from date;

CREATE TABLE BookingDetails (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    train_name VARCHAR(255),
    train_number VARCHAR(255),
    timings VARCHAR(255),
    fare VARCHAR(255),
    people VARCHAR(255),
    seat_numbers VARCHAR(255)
);

select * from BookingDetails;

CREATE TABLE BusDetails (
    bookingid INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    bus_name VARCHAR(255),
    bus_number VARCHAR(255),
    timings VARCHAR(255),
    fare VARCHAR(255),
    people VARCHAR(255),
    seat_numbers VARCHAR(255)
);

select * from BusDetails;

CREATE TABLE CabDetails (
    bookingid INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(20),
    cab_name VARCHAR(255),
    cab_number VARCHAR(255),
    timings VARCHAR(255),
    fare VARCHAR(255),
    people VARCHAR(255)
);

select * from CabDetails;
