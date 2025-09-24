alter table payments
    modify student_id int null;

alter table payments
    drop foreign key payments_ibfk_1;

alter table payments
    add constraint payments_ibfk_1
        foreign key (student_id) references students (id)
            on delete set null;
