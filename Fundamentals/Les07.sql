--Q1:
-- First create the table:
BEGIN
    EXECUTE IMMEDIATE 'create table top_salaries (
        salary number
        )';
END;
/

-- Then Insert into it the desired values:
DECLARE
    N NUMBER := &VAL;
    CURSOR CUR IS (
        SELECT
            DISTINCT SALARY,
            DENSE_RANK() OVER(ORDER BY SALARY DESC) AS SAL_RANK
        FROM
            EMPLOYEES
    );
BEGIN
    FOR REC IN CUR LOOP
        EXIT WHEN REC.SAL_RANK > N;
        INSERT INTO TOP_SALARIES VALUES(
            REC.SALARY
        );
    END LOOP;
END;
/

-- Finally show what we have done:
SELECT
    *
FROM
    TOP_SALARIES;

--Q2:
DECLARE
    DEPTNO NUMBER := &VAL;
    CURSOR CUR IS (
        SELECT
            LAST_NAME,
            MANAGER_ID,
            SALARY
        FROM
            EMPLOYEES
        WHERE
            DEPARTMENT_ID = DEPTNO
    );
BEGIN
    FOR REC IN CUR LOOP
        IF REC.SALARY < 5000 AND REC.MANAGER_ID IN (101, 124) THEN
            DBMS_OUTPUT.PUT_LINE(REC.LAST_NAME
                                 ||' Due for a raise');
        ELSE
            DBMS_OUTPUT.PUT_LINE(REC.LAST_NAME
                                 ||' Not due for a raise');
        END IF;
    END LOOP;
END;
/