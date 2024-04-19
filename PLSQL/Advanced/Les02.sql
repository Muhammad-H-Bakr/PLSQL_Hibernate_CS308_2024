set SERVEROUTPUT on;

-- Q1:
CREATE OR REPLACE FUNCTION GET_JOB (
    JOB VARCHAR2
) RETURN VARCHAR2 IS
    TITLE JOBS.JOB_TITLE%TYPE;
BEGIN
    SELECT
        JOB_TITLE INTO TITLE
    FROM
        JOBS
    WHERE
        JOB_ID = JOB;
    RETURN TITLE;
END;
/

SELECT
    GET_JOB('&job_id') "Title"
FROM
    DUAL;

-- Q2:
CREATE OR REPLACE FUNCTION GET_ANNUAL_COM(
    SAL EMPLOYEES.SALARY%TYPE,
    COM EMPLOYEES.COMMISSION_PCT%TYPE
) RETURN NUMBER IS
BEGIN
    RETURN (NVL(SAL, 0)*12)*(1+NVL(COM, 0));
END;
/

SELECT
    EMPLOYEE_ID,
    LAST_NAME,
    GET_ANNUAL_COM(SALARY, COMMISSION_PCT) "Annual Compensation"
FROM
    EMPLOYEES
WHERE
    DEPARTMENT_ID = 30;

-- Q3:
CREATE OR REPLACE FUNCTION VALID_DEPTID (
    DEPTID NUMBER
) RETURN BOOLEAN IS
    VAL NUMBER;
BEGIN
    SELECT
        COUNT(*) INTO VAL
    FROM
        DEPARTMENTS
    WHERE
        DEPARTMENT_ID = DEPTID;
    RETURN VAL = 1;
END;
/

-- Q4:
CREATE OR REPLACE PROCEDURE ADD_EMPLOYEE(
    F_NAME VARCHAR2,
    L_NAME VARCHAR2,
    MAIL VARCHAR2,
    JOB VARCHAR2 := 'SA_REP',
    MGR NUMBER := 145,
    SAL NUMBER := 1000,
    COMM NUMBER := 0,
    DEPTID NUMBER := 30
) IS
BEGIN
    IF(VALID_DEPTID(DEPTID)) THEN
        INSERT INTO EMPLOYEES(
            EMPLOYEE_ID,
            FIRST_NAME,
            LAST_NAME,
            EMAIL,
            JOB_ID,
            MANAGER_ID,
            SALARY,
            COMMISSION_PCT,
            DEPARTMENT_ID,
            HIRE_DATE
        ) VALUES(
            EMPLOYEES_SEQ.NEXTVAL,
            F_NAME,
            L_NAME,
            MAIL,
            JOB,
            MGR,
            SAL,
            COMM,
            DEPTID,
            TRUNC(SYSDATE())
        );
    END IF;
END;
/

BEGIN
    ADD_EMPLOYEE(
        F_NAME => 'Joe',
        L_NAME => 'Harris',
        DEPTID => 80, -- Invalid departments will neither cause an error nor will it insert.
        MAIL => 'JaneHarris1992@gmail.com' -- Must put or mail otherwise not enough arguments error.
    );
END;
/