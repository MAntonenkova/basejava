create table resume
(
    uuid      char(36) not null
        constraint resume_pk
            primary key,
    full_name text     not null
);

alter table resume
    owner to postgres;

create table contact
(
    id        serial   not null
        constraint contact_pk
            primary key,

    resume_id char(36) not null
        constraint contact____resume_uuid_fk
            references resume
            on update restrict on delete cascade,
    type      text     not null,
    value     text     not null
);

alter table contact
    owner to postgres;

create unique index contact__uuid_type_index
    on contact (resume_id, type);



