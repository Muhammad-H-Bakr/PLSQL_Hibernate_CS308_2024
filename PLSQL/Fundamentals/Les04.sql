--Q1:
DECLARE
    MAX_DEPTNO NUMBER;
BEGIN
    SELECT
        MAX(DEPARTMENT_ID) INTO MAX_DEPTNO
    FROM
        DEPARTMENTS;
    DBMS_OUTPUT.PUT_LINE('The maximum department_id is : '
                         ||MAX_DEPTNO);
END;
 --Q2:
VARIABLE DEPT_ID NUMBER;
DECLARE
    DEPT_NAME DEPARTMENTS.DEPARTMENT_NAME%TYPE := 'Education';
BEGIN
    SELECT
        MAX(DEPARTMENT_ID) + 10 INTO :DEPT_ID
    FROM
        DEPARTMENTS;
    DBMS_OUTPUT.PUT_LINE('The maximum department_id is : '
                         ||(:DEPT_ID - 10));
    INSERT INTO DEPARTMENTS VALUES(
        :DEPT_ID,
        DEPT_NAME,
        NULL,
        NULL
    );
    DBMS_OUTPUT.PUT_LINE('SQL%ROWCOUNT gives '
                         || SQL%ROWCOUNT);
END;
 --Show what we have done:
SELECT
    *
FROM
    DEPARTMENTS;
 --Undo changes to the hr schema before next question:
ROLLBACK;
 --Q3:
 --insert:
VARIABLE DEPT_ID NUMBER;
DECLARE
    DEPT_NAME DEPARTMENTS.DEPARTMENT_NAME%TYPE := 'Education';
BEGIN
    SELECT
        MAX(DEPARTMENT_ID) + 10 INTO :DEPT_ID
    FROM
        DEPARTMENTS;
    INSERT INTO DEPARTMENTS VALUES(
        :DEPT_ID,
        DEPT_NAME,
        NULL,
        NULL
    );
END;
/

--update:
BEGIN
    UPDATE DEPARTMENTS
    SET
        LOCATION_ID = 3000
    WHERE
        DEPARTMENT_ID = :DEPT_ID;
END;
/

--delete:
BEGIN
    DELETE FROM DEPARTMENTS
    WHERE
        DEPARTMENT_ID = :DEPT_ID;
END;