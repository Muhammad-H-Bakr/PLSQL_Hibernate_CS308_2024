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

    PROCEDURE ADD_EMPLOYEE(
        FNAME VARCHAR2,
        LNAME VARCHAR2,
        DEPT NUMBER
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
 -- Overloading Procedure:
    PROCEDURE ADD_EMPLOYEE(
        FNAME VARCHAR2,
        LNAME VARCHAR2,
        DEPT NUMBER
    ) IS
    BEGIN
        ADD_EMPLOYEE (F_NAME => FNAME, L_NAME => LNAME, MAIL => (SUBSTR(FNAME, 0, 1)
                                                                 || SUBSTR(LNAME, 0, 7)), DEPTID => DEPT);
    END ADD_EMPLOYEE;
END EMP_PKG;
/

-- Test:
BEGIN
    EMP_PKG.ADD_EMPLOYEE('Samuel', 'Joplin', 30);
END;
/

-- Query:
SELECT
    *
FROM
    EMPLOYEES;

-- DO NOT FORGET TO RUN THE HR SCHEMA AFTER YOU MANIPULATE THE TABLES.

-- Q2:
CREATE OR REPLACE PACKAGE EMP_PKG IS

    FUNCTION GET_EMPLOYEE(
        EMP_ID EMPLOYEES.EMPLOYEE_ID%TYPE
    ) RETURN EMPLOYEES%ROWTYPE;

    FUNCTION GET_EMPLOYEE(
        FAMILY_NAME EMPLOYEES.LAST_NAME%TYPE
    ) RETURN EMPLOYEES%ROWTYPE;

    PROCEDURE PRINT_EMPLOYEE(
        EMP EMPLOYEES%ROWTYPE
    );
END EMP_PKG;
/

CREATE OR REPLACE PACKAGE BODY EMP_PKG IS

    FUNCTION GET_EMPLOYEE(
        EMP_ID EMPLOYEES.EMPLOYEE_ID%TYPE
    ) RETURN EMPLOYEES%ROWTYPE IS
        REC EMPLOYEES%ROWTYPE;
    BEGIN
        SELECT
            * INTO REC
        FROM
            EMPLOYEES
        WHERE
            EMPLOYEE_ID = EMP_ID;
        RETURN REC;
    END GET_EMPLOYEE;

    FUNCTION GET_EMPLOYEE(
        FAMILY_NAME EMPLOYEES.LAST_NAME%TYPE
    ) RETURN EMPLOYEES%ROWTYPE IS
        REC EMPLOYEES%ROWTYPE;
    BEGIN
        SELECT
            * INTO REC
        FROM
            EMPLOYEES
        WHERE
            LAST_NAME = FAMILY_NAME;
        RETURN REC;
    END GET_EMPLOYEE;

    PROCEDURE PRINT_EMPLOYEE(
        EMP EMPLOYEES%ROWTYPE
    ) IS
    BEGIN
        DBMS_OUTPUT.PUT_LINE(EMP.EMPLOYEE_ID
                             || ' '
                             || EMP.FIRST_NAME
                             || ' '
                             ||EMP.LAST_NAME
                             || ' '
                             ||EMP.JOB_ID);
    END PRINT_EMPLOYEE;
END EMP_PKG;
/

BEGIN
    EMP_PKG.PRINT_EMPLOYEE(EMP_PKG.GET_EMPLOYEE(100));
 --  EMP_PKG.PRINT_EMPLOYEE(EMP_PKG.GET_EMPLOYEE('Joplin'));
END;
/