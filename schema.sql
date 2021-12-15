create table if not exists file_info
(
    id                 serial       not null primary key,
    original_file_name varchar(100),
    storage_file_name  varchar(100) not null,
    size               bigint       not null,
    type               varchar(100)
);

create table if not exists users
(
    id            serial       not null primary key,
    first_name    varchar(20),
    last_name     varchar(20),
    age           integer,
    gender        varchar(10),
    password_hash varchar(100) not null,
    email         varchar(100) not null unique,
    description   varchar(255),
    avatar_id     integer references file_info
);

create table user_token
(
    user_id int primary key references users,
    token   varchar(200) not null
);


create table if not exists dialogs
(
    id           serial primary key,
    user_1       int       not null references users,
    user_2       int       not null references users,
    last_message timestamp not null
);

create table if not exists messages
(
    id         serial primary key,
    author_id  int          not null references users,
    created_at timestamp    not null,
    content    varchar(255) not null,
    dialog_id  int          not null references dialogs
);

create table if not exists matches
(
    user_id  integer not null references users,
    match_id integer not null references users
);

create table if not exists interacted
(
    user_id integer not null references users,
    interacted_with_id integer not null references users
)
