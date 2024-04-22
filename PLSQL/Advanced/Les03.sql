-- Q1:
-- Package:
CREATE OR REPLACE PACKAGE JOB_PKG IS

    FUNCTION GET_JOB (
        JOB VARCHAR2
    ) RETURN VARCHAR2;

    PROCEDURE ADD_JOB (
        ID VARCHAR2,
        TITLE VARCHAR2
    );

    PROCEDURE UPD_JOB (
        ID VARCHAR2,
        TITLE VARCHAR2
    );

    PROCEDURE DEL_JOB (
        ID VARCHAR2
    );
END JOB_PKG;
/

-- Package body:
CREATE OR REPLACE PACKAGE BODY JOB_PKG IS

    FUNCTION GET_JOB (
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
    END GET_JOB;

    PROCEDURE ADD_JOB (
        ID VARCHAR2,
        TITLE VARCHAR2
    ) IS
    BEGIN
        INSERT INTO JOBS(
            JOB_ID,
            JOB_TITLE
        ) VALUES (
            ID,
            TITLE
        );
    END ADD_JOB;

    PROCEDURE UPD_JOB (
        ID VARCHAR2,
        TITLE VARCHAR2
    ) IS
    BEGIN
        UPDATE JOBS
        SET
            JOB_TITLE = TITLE
        WHERE
            JOB_ID = ID;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
    END UPD_JOB;

    PROCEDURE DEL_JOB (
        ID VARCHAR2
    )IS
    BEGIN
        DELETE FROM JOBS
        WHERE
            JOB_ID = ID;
    EXCEPTION
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE(SQLERRM);
    END DEL_JOB;
END JOB_PKG;
/

-- Test:
BEGIN
    JOB_PKG.ADD_JOB('IT_SYSAN', 'SYSTEMS ANALYST ');
END;
/

-- Query:
SELECT
    *
FROM
    JOBS;

--Return things to its usual:
DELETE FROM JOBS
WHERE
    JOB_ID = 'IT_SYSAN';

-- Q2:
-- Package:
CREATE OR REPLACE PACKAGE EMP_PKG IS

    PROCEDURE ADD_EMPLOYEE(
        F_NAME VARCHAR2,
        L_NAME VARCHAR2,
        MAIL VARCHAR2,
        JOB VARCHAR2 := 'SA_REP',
        MGR NUMBER := 145,
        SAL NUMBER := 1000,
        COMM NUMBER := 0,
        DEPTID NUMBER := 30
    );

    PROCEDURE GET_EMPLOYEE (
        ID NUMBER
    );
END EMP_PKG;
/

-- Package body:
CREATE OR REPLACE PACKAGE BODY EMP_PKG IS

    PROCEDURE GET_EMPLOYEE (
        ID NUMBER
    ) AS
        REC EMPLOYEES%ROWTYPE;
    BEGIN
        SELECT
            * INTO REC
        FROM
            EMPLOYEES
        WHERE
            EMPLOYEE_ID = ID;
        DBMS_OUTPUT.PUT_LINE('Salary: '
                             ||REC.SALARY);
        DBMS_OUTPUT.PUT_LINE('JOB_ID: '
                             ||REC.JOB_ID);
    END GET_EMPLOYEE;
 -- Private function:
    FUNCTION VALID_DEPTID (
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
    END VALID_DEPTID;

    PROCEDURE ADD_EMPLOYEE(
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
    END ADD_EMPLOYEE;
END EMP_PKG;
/

-- Test1:
BEGIN
    EMP_PKG.ADD_EMPLOYEE('Jane', 'Harris', 'JAHarris', DEPTID => 15);
END;
/

-- Test2:
BEGIN
    EMP_PKG.ADD_EMPLOYEE('David', 'Smith', 'DASMITH', DEPTID => 80);
END;
/

-- Query:
SELECT
    *
FROM
    EMPLOYEES;