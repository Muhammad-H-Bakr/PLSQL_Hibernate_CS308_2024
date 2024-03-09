--Must run this statement first for only one time:
set serveroutput on;

--Q1: print the max salary of the accounting department. (Can be solved by subquery)
variable max_salary number;
begin
select max(salary) 
into :max_salary
from employees 
join departments 
on employees.department_id = departments.department_id
group by department_name
having department_name = 'Accounting';
end;
/
print max_salary

--Q2: print the manager of the accounting department. (Can be solved by subquery)
declare manager employees.first_name%type;
begin
select first_name
into manager
from employees
where employee_id = (select manager_id from departments where department_name = 'Accounting');
dbms_output.put_line(manager);
end;
/

--Q3: find the second max salary in the employees table.
select max(salary) from employees
where salary in (select salary from employees minus select max(Salary) from employees);

--Q4: print the first name of the manager for 'Mourad' [Yeilds an error because "Mourad" is not in the employees table]
--(Can be solved by subquery.)
Declare manager_name employees.first_name%type;
begin
select man.first_name
into manager_name
from employees emp
join employees man
on emp.manager_id = man.employee_id
where emp.first_name = 'Mourad';
dbms_output.put_line(manager_name);
end;
/


