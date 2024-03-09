/*Write a PL/SQL anonymous block that creates a table named tempTable with attributes: 
id a number that is the primary key
name varchar2 which is not null
salary number that has to be greater than zero.
*/
begin
    execute immediate '
    create table tempTable(
    id number primary key,
    name varchar2(20) not null,
    salary number check (salary > 0)
    )
    ';
end;

-- Now enter 2 recodrs and presist the data:
begin
     insert into tempTable
     values(1,'Bakr',20000);
     insert into tempTable
     values(2,'M7md',30000);
     commit;
end;

-- A query to test each step.
select * from tempTable;

-- Now delete those 2 records permanently:
begin
     execute immediate'
     truncate table tempTable
     ';
end; 