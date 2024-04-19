set SERVEROUTPUT on;

--Q1:
CREATE OR REPLACE PROCEDURE ADD_JOB (
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
END;
/

BEGIN
    ADD_JOB('IT_DBA', 'Database Adminstrator');
END;
/

--Q2:
CREATE OR REPLACE PROCEDURE UPD_JOB (
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
END;
/

BEGIN
    UPD_JOB('IT_DBA', 'Data Adminstrator');
    UPD_JOB('IT_WEB ', 'Web Master');
END;
/

--Q3:
CREATE OR REPLACE PROCEDURE DEL_JOB (
    ID VARCHAR2
) IS
BEGIN
    DELETE FROM JOBS
    WHERE
        JOB_ID = ID;
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE(SQLERRM);
END;
/

BEGIN
    DEL_JOB('IT_DBA');
    DEL_JOB('IT_WEB');
END;
/

--Q4:
CREATE OR REPLACE PROCEDURE GET_EMPLOYEE (
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
END;
/

BEGIN
    GET_EMPLOYEE(&NUM);
END;
/