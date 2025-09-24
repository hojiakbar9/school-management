alter table students
    drop foreign key students_ibfk_1;

alter table students
    add constraint students_ibfk_1
        foreign key (parent_id) references parents (id);


alter table students
    modify parent_id int not null;



