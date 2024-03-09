--Q1: Group workers by year using hire_date, also obtain the number of those who worked in these years.
select to_char(hire_date,'YYYY'), count(*)
from employees
group by to_char(hire_date,'YYYY');

--Q2: Get each employee's first name and their manager first name (ignore the case where there is someone with no manager.)
select manager.first_name "Manager", employee.first_name "Employee"
from employees manager join employees employee on (manager.employee_id = employee.manager_id);

--Q3: get the first name and salary of those who earn higher than Steven and their name ends with 'a'.
select first_name, salary
from employees
where salary > All (select salary from employees where first_name = 'Steven') and last_name like '%a'; 

--helper Query for Q3 (explaining why Q3 yields no data set.)
select first_name, salary
from employees
where salary = (select max(salary) from employees);
