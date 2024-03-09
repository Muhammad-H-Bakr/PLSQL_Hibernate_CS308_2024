--Q1:
BEGIN
    EXECUTE IMMEDIATE 'create table messages(numbers number)';
END;

DECLARE
    I NUMBER;
BEGIN
    FOR I IN 1 .. 10 LOOP
        IF I != 6 AND I!= 8 THEN
            INSERT INTO MESSAGES VALUES(
                I
            );
        END IF;
    END LOOP;
END;

--Q2:
BEGIN
    EXECUTE IMMEDIATE 'create table emp(
        employee_id number,
        salary number,
        stars varchar2(50) )';
END;

DECLARE
    EMPNO    NUMBER := 176;
    ASTERISK VARCHAR2(50) := '';
    SAL      NUMBER;
    RATIO    NUMBER;
BEGIN
    SELECT
        SALARY INTO SAL
    FROM
        EMPLOYEES
    WHERE
        EMPLOYEE_ID = EMPNO;
    RATIO := SAL/1000;
    LOOP
        ASTERISK := ASTERISK||'*';
        RATIO := RATIO - 1;
        EXIT WHEN RATIO < 0;
    END LOOP;

    INSERT INTO EMP VALUES (
        EMPNO,
        SAL,
        ASTERISK
    );
END;