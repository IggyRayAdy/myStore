create table cart
(
    id           bigint  not null auto_increment,
    cash_balance integer not null,
    usr_id       bigint,
    primary key (id)
) engine = InnoDB;
create table ord
(
    id      bigint  not null auto_increment,
    active  bit     not null,
    cost    integer not null,
    cart_id bigint,
    primary key (id)
) engine = InnoDB;
create table order_order_map
(
    order_id      bigint not null,
    order_map     bigint,
    order_map_key bigint not null,
    primary key (order_id, order_map_key)
) engine = InnoDB;
create table prod
(
    id              bigint  not null auto_increment,
    cost            integer not null,
    description     varchar(14),
    discount        integer not null,
    file_name       varchar(255),
    label           varchar(8),
    name            varchar(30),
    type_of_product varchar(255),
    primary key (id)
) engine = InnoDB;
create table usr
(
    id              bigint not null auto_increment,
    activation_code varchar(255),
    active          bit    not null,
    email           varchar(255),
    password        varchar(255),
    username        varchar(16),
    primary key (id)
) engine = InnoDB;
create table usr_role
(
    usr_id    bigint not null,
    user_role varchar(255)
) engine = InnoDB;
alter table cart
    add constraint FK4oqxl7tpbe965mxunc8eckboy foreign key (usr_id) references usr (id);
alter table ord
    add constraint FKef3xg2ao8fpac126ahwb3vlh9 foreign key (cart_id) references cart (id);
alter table order_order_map
    add constraint FK6d9x79e8trjk7h701a6u6h6j9 foreign key (order_id) references ord (id);
alter table usr_role
    add constraint FK9ffk6ts9njcytrt8ft17fvr3p foreign key (usr_id) references usr (id);
