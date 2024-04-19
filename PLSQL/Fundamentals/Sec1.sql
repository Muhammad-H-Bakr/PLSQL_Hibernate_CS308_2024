--Must run this statement first for only one time:
set serveroutput on;

--Q1: print the max salary of the accounting department. (Can be solved by subquery)
variable max_salary number;

BEGIN
    SELECT
        MAX(SALARY) INTO :MAX_SALARY
    FROM
        EMPLOYEES
        JOIN DEPARTMENTS
        ON EMPLOYEES.DEPARTMENT_ID = DEPARTMENTS.DEPARTMENT_ID
    GROUP BY
        DEPARTMENT_NAME
    HAVING
        DEPARTMENT_NAME = 'Accounting';
END;
/

print max_salary

--Q2: print the manager of the accounting department. (Can be solved by subquery)
DECLARE
    MANAGER EMPLOYEES.FIRST_NAME%TYPE;
BEGIN
    SELECT
        FIRST_NAME INTO MANAGER
    FROM
        EMPLOYEES
    WHERE
        EMPLOYEE_ID = (
            SELECT
                MANAGER_ID
            FROM
                DEPARTMENTS
            WHERE
                DEPARTMENT_NAME = 'Accounting'
        );
    DBMS_OUTPUT.PUT_LINE(MANAGER);
END;
/

--Q3: find the second max salary in the employees table.
SELECT
    MAX(SALARY)
FROM
    EMPLOYEES
WHERE
    SALARY IN (
        SELECT
            SALARY
        FROM
            EMPLOYEES MINUS
            SELECT
                MAX(SALARY)
            FROM
                EMPLOYEES
    );

--Q4: print the first name of the manager for 'Mourad' [Yeilds an error because "Mourad" is not in the employees table]
--(Can be solved by subquery.)
DECLARE
    MANAGER_NAME EMPLOYEES.FIRST_NAME%TYPE;
BEGIN
    SELECT
        MAN.FIRST_NAME INTO MANAGER_NAME
    FROM
        EMPLOYEES EMP
        JOIN EMPLOYEES MAN
        ON EMP.MANAGER_ID = MAN.EMPLOYEE_ID
    WHERE
        EMP.FIRST_NAME = 'Mourad';
    DBMS_OUTPUT.PUT_LINE(MANAGER_NAME);
END;
/