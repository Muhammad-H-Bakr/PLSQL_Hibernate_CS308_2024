--Q1:
declare 
    max_deptno number;
begin
    select max(department_id)
    into max_deptno
    from departments;
    dbms_output.put_line('The maximum department_id is : '||max_deptno);
end;

--Q2:
variable dept_id number;
declare
       dept_name departments.department_name%type := 'Education';
begin
     select max(department_id) + 10
     into :dept_id
     from departments;
     dbms_output.put_line('The maximum department_id is : '||(:dept_id - 10));
     insert into departments 
     values(:dept_id, dept_name, null, null);
     dbms_output.put_line('SQL%ROWCOUNT gives ' || SQL%ROWCOUNT);
end;
--Show what we have done:
select * from departments;
--Undo changes to the hr schema before next question:
rollback;

--Q3: 
--insert:
variable dept_id number;
declare
       dept_name departments.department_name%type := 'Education';
begin
     select max(department_id) + 10
     into :dept_id
     from departments;
     insert into departments 
     values(:dept_id, dept_name, null, null);
end;
/
--update:
begin
     update departments 
     set location_id = 3000
     where department_id = :dept_id;
end;
/
--delete:
begin 
    delete from departments
    where department_id = :dept_id;
end;




