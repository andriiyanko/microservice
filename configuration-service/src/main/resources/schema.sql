create database microservice;
\c microservice;
create table configuration (
    id serial primary key,
    serial_number character varying(40) unique not null,
    ip_address character varying(16) not null,
    subnet_mask character varying(16) not null,
    check( (serial_number !='') and (ip_address !='') and (subnet_mask != ''))
);
