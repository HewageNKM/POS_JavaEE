
# POS JavaEE

This is a Point of Sales System developed using JavaEE.


## Documentation

- Install MySQL and create a database called POS.
- Create tables using the SQL script given below.
- Install Node.js and run the following commands in the terminal.
  ````
    npm install -g serve
-  In the Frontend folder run the following command.
   ```` 
    serve
-  Run the project in the IDE.
- Open the browser and go to http://localhost:3000 to access the application.
- Use the following credentials to login.
  ````
    username: root
    password: 1234
-  You can change the credentials in reset form.
- You can add new users using the application.
- You can add new customers, items, orders and order details using the application.
---
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

