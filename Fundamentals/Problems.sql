-- Write a PL/SQL block that checks whether a given number from the user is prime.
DECLARE
    USER_INPUT   NUMBER := &NUMBER;
    LOOP_COUNTER NUMBER := USER_INPUT-1;
    FLAG         NUMBER := 0;
BEGIN
    IF USER_INPUT > 2 THEN
        LOOP
            IF USER_INPUT MOD LOOP_COUNTER = 0 THEN
                FLAG := 1;
            END IF;

            LOOP_COUNTER := LOOP_COUNTER - 1;
            EXIT WHEN LOOP_COUNTER = 1 OR FLAG = 1;
        END LOOP;
    ELSIF USER_INPUT <= 1 THEN
        FLAG := 1;
    END IF;

    IF FLAG = 0 THEN
        DBMS_OUTPUT.PUT_LINE('Prime.');
    ELSIF FLAG = 1 THEN
        DBMS_OUTPUT.PUT_LINE('Not Prime.');
    END IF;
END;
/

/* Write a PL/SQL block that prints the largest and smallest in an array.
type arr is varray(5) of number;
myArr arr := arr(10,4,6,22,11); */
DECLARE
    TYPE ARR IS
        VARRAY(5) OF NUMBER;
    MYARR ARR := ARR(10, 4, 6, 22, 11);
    MN    NUMBER := MYARR(1);
    MX    NUMBER := 0;
BEGIN
    FOR I IN 1..MYARR.COUNT LOOP
        IF MYARR(I) > MX THEN
            MX := MYARR(I);
        END IF;

        IF MYARR(I) < MN THEN
            MN := MYARR(I);
        END IF;
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('Max: '
                         ||MX
                         ||', Min: '
                         ||MN);
END;
/

/* Write a PL/SQL block that prints every number and its frequency in an array.
Eg: type arr is varray(6) of number;
myArr arr := arr(10,10,10,6,6,11); */
DECLARE
    TYPE ARR IS
        VARRAY(6) OF NUMBER;
    MYARR   ARR := ARR(10, 10, 10, 6, 6, 11);
    TYPE HELPER IS
        TABLE OF NUMBER INDEX BY PLS_INTEGER;
    FREQ    HELPER;
    COUNTER NUMBER;
BEGIN
    FOR I IN 1..MYARR.COUNT LOOP
        IF NOT (FREQ.EXISTS(MYARR(I))) THEN
            FREQ(MYARR(I)) := 1;
        ELSE
            FREQ(MYARR(I)) := FREQ(MYARR(I)) + 1;
        END IF;
    END LOOP;

    FOR I IN FREQ.FIRST .. FREQ.LAST LOOP
        IF FREQ.EXISTS(I) THEN
            IF FREQ(I) = 1 THEN
                DBMS_OUTPUT.PUT_LINE('The number '
                                     ||I
                                     ||' appears one time.');
            ELSE
                DBMS_OUTPUT.PUT_LINE('The number '
                                     ||I
                                     ||' appears '
                                     ||FREQ(I)
                                     ||' times.');
            END IF;
        END IF;
    END LOOP;
END;
/

-- Write a PL/SQL block that takes a number n from the user and outputs the nth term in Fibonacci's sequence.
DECLARE
    USER_INPUT NUMBER := &VAL;
    PREV       NUMBER := 0;
    MID        NUMBER;
    CURR       NUMBER := 1;
BEGIN
    IF(USER_INPUT = 0) THEN
        DBMS_OUTPUT.PUT_LINE(PREV);
    ELSIF(USER_INPUT = 1) THEN
        DBMS_OUTPUT.PUT_LINE(CURR);
    ELSE
        FOR I IN 2..USER_INPUT LOOP
            MID := PREV;
            PREV := CURR;
            CURR := CURR + MID;
        END LOOP;

        DBMS_OUTPUT.PUT_LINE(CURR);
    END IF;
END;
/

-- Write a PL/SQL block to print the first_name and the salary for all the employees using explicit cursors.
DECLARE
    CURSOR CUR IS (
        SELECT
            FIRST_NAME,
            SALARY
        FROM
            EMPLOYEES
    );
BEGIN
    FOR REC IN CUR LOOP
        DBMS_OUTPUT.PUT_LINE('Name: '
                             ||REC.FIRST_NAME
                             ||', salary: '
                             ||REC.SALARY);
    END LOOP;
END;
/

/* The user is prompted to enter a date in the past.
Write a PL/SQL that prints the number of employees who are hired after the specified date.
When the given date is greater than or equal to the current date an exception should be triggered...
printing the following message “invalid date”. */
DECLARE
    USER_INPUT DATE := '&VAL';
    OUTPUT     NUMBER;
BEGIN
    IF USER_INPUT >= TO_DATE(SYSDATE) THEN
        RAISE_APPLICATION_ERROR(-20001, 'Invalid Date.');
    ELSE
        SELECT
            COUNT(*) INTO OUTPUT
        FROM
            EMPLOYEES
        WHERE
            HIRE_DATE > USER_INPUT;
        DBMS_OUTPUT.PUT_LINE('#Of Employees: '
                             ||OUTPUT
                             ||'.');
    END IF;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

-- First Hard SQL Problem on LeetCode: https://leetcode.com/problems/department-top-three-salaries/description/.
SELECT
    DEP_NAME "Department",
    EMP_NAME "Employee",
    SALARY   "Salary"
FROM
    (
        SELECT
            SALARY,
            D.NAME                                                        AS DEP_NAME,
            E.NAME                                                        AS EMP_NAME,
            DENSE_RANK() OVER(PARTITION BY D.NAME ORDER BY E.SALARY DESC) AS RANKED
        FROM
            EMPLOYEE   E
            JOIN DEPARTMENT D
            ON (E.DEPARTMENTID = D.ID)
    )
WHERE
    RANKED <= 3
ORDER BY
    3 DESC;

-- Second Hard SQL Problem on LeetCode: https://leetcode.com/problems/trips-and-users/description/.
SELECT
    REQUEST_AT                                                    "Day",
    ROUND(1-(SUM(DECODE(STATUS, 'completed', 1, 0))/COUNT(*)), 2) "Cancellation Rate"
FROM
    TRIPS
WHERE
    CLIENT_ID NOT IN (
        SELECT
            USERS_ID
        FROM
            USERS
        WHERE
            BANNED = 'Yes'
    )
    AND DRIVER_ID NOT IN (
        SELECT
            USERS_ID
        FROM
            USERS
        WHERE
            BANNED = 'Yes'
    )
    AND REQUEST_AT BETWEEN '2013-10-01' AND '2013-10-03'
GROUP BY
    REQUEST_AT
ORDER BY
    1;

-- Third Hard SQL Problem on LeetCode: https://leetcode.com/problems/human-traffic-of-stadium/description/.
WITH CTE AS (
    SELECT
        GRP
    FROM
        (
            SELECT
                ID - ROW_NUMBER() OVER (ORDER BY ID) AS GRP
            FROM
                STADIUM
            WHERE
                PEOPLE >= 100
        )
    GROUP BY
        GRP
    HAVING
        COUNT(GRP) >= 3
)
SELECT
    ID                  "id",
    TO_CHAR(VISIT_DATE) "visit_date",
    PEOPLE              "people"
FROM
    (
        SELECT
            ID,
            VISIT_DATE,
            PEOPLE,
            ID - ROW_NUMBER() OVER (ORDER BY ID) AS QQ
        FROM
            STADIUM
        WHERE
            PEOPLE >= 100
    )
WHERE
    QQ IN (
        SELECT
            GRP
        FROM
            CTE1
    )
ORDER BY
    1;

/* Write a PL/SQL block that prints the first_name, their salary, their manager's last_name
and their department name for the first 10 employees sorted by the salary descendingly using cursors. */
DECLARE
    CURSOR CUR IS (
        SELECT
            E.FIRST_NAME                               AS FNAME,
            E.SALARY                                   AS PAY,
            M.LAST_NAME                                AS LNAME,
            D.DEPARTMENT_NAME                          AS DNAME,
            ROW_NUMBER() OVER (ORDER BY E.SALARY DESC) SAL
        FROM
            EMPLOYEES   E
            JOIN EMPLOYEES M
            ON (E.MANAGER_ID = M.EMPLOYEE_ID)
            JOIN DEPARTMENTS D
            ON (E.DEPARTMENT_ID = D.DEPARTMENT_ID)
    );
BEGIN
    FOR REC IN CUR LOOP
        EXIT WHEN REC.SAL > 10;
        DBMS_OUTPUT.PUT_LINE('First Name: '
                             ||REC.FNAME
                             ||', salary: '
                             ||REC.PAY
                             || ', last name of the manager: '
                             ||REC.LNAME
                             ||', department name: '
                             ||REC.DNAME
                             ||'.');
    END LOOP;
END;
/

/* Write a PL/SQL block that declares a custom exception and creates a table named "MyTestTable"
with the columns id(primary key) and name(not null). The block tries to delete all the records
in the newly created table but if there are no records to delete it raises an exception
with the error message "There are no records to delete". */

DECLARE
    SZ NUMBER;
    EX EXCEPTION;
    PRAGMA EXCEPTION_INIT(EX, -20002);
BEGIN
    SELECT
        COUNT(*) INTO SZ
    FROM
        MYTESTTABLE;
    IF SZ = 0 THEN
        RAISE EX;
    ELSE
        DELETE FROM MYTESTTABLE;
    END IF;
EXCEPTION
    WHEN EX THEN
        DBMS_OUTPUT.PUT_LINE('Error '
                             ||SQLCODE
                             ||': There are no records to delete.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

-- Implement Fibonacci using a function recursively:
CREATE OR REPLACE FUNCTION FIBONACCI (
    N NUMBER
) RETURN NUMBER IS
BEGIN
    IF N = 0 THEN
        RETURN 0;
    ELSIF N = 1 THEN
        RETURN 1;
    ELSE
        RETURN FIBONACCI(N-1) + FIBONACCI(N-2);
    END IF;
END;
/

BEGIN
    DBMS_OUTPUT.PUT_LINE(FIBONACCI(&NUM));
END;
/
