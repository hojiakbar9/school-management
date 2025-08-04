alter table students
    modify gender enum ('MALE', 'FEMALE') not null;

alter table students
    modify date_of_birth date not null;