create table users (
   id                      bigserial primary key,
   username                varchar(30) not null unique,
   password                varchar(80) not null,
   email                   varchar(50) unique,
   created_at              timestamp default current_timestamp,
   updated_at              timestamp default current_timestamp
);

create table roles (
                       id                      bigserial primary key,
                       name                    varchar(50) not null unique,
                       created_at              timestamp default current_timestamp,
                       updated_at              timestamp default current_timestamp
);

CREATE TABLE users_roles (
                             user_id    bigint not null references users (id),
                             role_id    bigint not null references roles (id),
                             primary key (user_id, role_id)
);

insert into roles (name)
values
('ROLE_FREE_USER'),
('ROLE_PREMIUM_USER');

insert into users (username, password, email)
values
('BillyBones', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'BillyBones@gmail.com'), /* 100 */
('RedSonya', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Red_Sonya1987@gmail.com'); /* 100 ROLE_PREMIUM_USER */

insert into users_roles (user_id, role_id)
values
(1, 1),
(2, 2);

create table notes (
      id                      bigserial primary key,
      owner_id                bigint not null references users (id),
      article                 varchar(255),
      executed                bit,
      created_at              timestamp default current_timestamp,
      updated_at              timestamp default current_timestamp
);

insert into notes(owner_id, article, executed)
values
(1, 'buy food', 0),
(1, 'take out the trash', 0),
(1, 'call a friend', 0),
(1, 'go to the gym', 1),
(2, 'еo do makeup', 0),
(2, 'go to spa', 1),
(2, 'call Billy', 1);