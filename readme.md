
# POS JavaEE

Web application with Node.js for front-end and JavaEE for back-end.


## Documentation

- Start front-end server by nodemon app.js (nodemon must be instlled).
- Start Tomcat server in the backend.
- Note: Intellij IDE
- Default username and password is root and 1234.
-----
## Reruire
- MySQL
- Node.js


## Tables

```bash
 create table customer
(
    ID      varchar(20) not null
        primary key,
    Name    varchar(50) not null,
    Address varchar(50) not null,
    Salary  int         not null
);

create table item
(
    ID    varchar(20) not null
        primary key,
    Name  varchar(50) not null,
    Price double      not null,
    QTY   int         not null
);

create table orders
(
    ID          varchar(20) null,
    CID         varchar(20) not null,
    order_total float       not null,
    discount    float       not null,
    Date        date        null,
    constraint Order_customer_ID_fk
        foreign key (CID) references customer (ID)
            on update cascade on delete cascade
);

create table order_details
(
    order_id  varchar(20) not null,
    item_id   varchar(20) not null,
    item_name varchar(50) not null,
    price     float       not null,
    qty       int         not null,
    total     float       not null,
    constraint order_details_item_ID_fk
        foreign key (item_id) references item (ID)
            on update cascade on delete cascade,
    constraint order_details_orders_ID_fk
        foreign key (order_id) references orders (ID)
            on update cascade on delete cascade
);

create index ID
    on orders (ID);

create table user
(
    username char(10) not null,
    password char(12) not null
);
insert into POS.user (username, password)
values ('root','1234');
```

## Authors

- [@Nadun Kawishika](https://www.github.com/HewageNKM)

