--Q1:
DECLARE
    COUNTRY_RECORD COUNTRIES%ROWTYPE;
    COUNTRYID      COUNTRIES.COUNTRY_ID%TYPE := '&COUNTRY_ID';
BEGIN
    SELECT
        * INTO COUNTRY_RECORD
    FROM
        COUNTRIES
    WHERE
        COUNTRY_ID = COUNTRYID;
    DBMS_OUTPUT.PUT_LINE('Country Name: '
                         ||COUNTRY_RECORD.COUNTRY_NAME
                         ||' Region: '
                         ||COUNTRY_RECORD.REGION_ID);
END;
/

--Q2:
DECLARE
    TYPE DEP_TABLE IS
        TABLE OF DEPARTMENTS.DEPARTMENT_NAME%TYPE INDEX BY PLS_INTEGER; -- With index = Map.
    MY_DEPT    DEP_TABLE; -- Don't need to Initialize.
    LOOP_COUNT NUMBER := 10;
BEGIN
    LOOP
        SELECT
            DEPARTMENT_NAME INTO MY_DEPT(LOOP_COUNT) -- Enters any index (But it's recommended to be sequential).
        FROM
            DEPARTMENTS
        WHERE
            DEPARTMENT_ID = LOOP_COUNT;
        DBMS_OUTPUT.PUT_LINE(MY_DEPT(LOOP_COUNT)); -- Indexes here are 10, 20, 30... etc.
        LOOP_COUNT := LOOP_COUNT + 10;
        EXIT WHEN LOOP_COUNT > 100;
    END LOOP;
END;
/

--Q2*:
DECLARE
    TYPE DEP_TABLE IS
        TABLE OF DEPARTMENTS.DEPARTMENT_NAME%TYPE; -- No index = Linked List.
    MY_DEPT    DEP_TABLE := DEP_TABLE(); -- Must Initialize to empty Collection otherwise it's NULL.
    LOOP_COUNT NUMBER := 10;
BEGIN
    LOOP
        MY_DEPT.EXTEND; -- Have to enlarge the size (it's initially 0).
        SELECT
            DEPARTMENT_NAME INTO MY_DEPT(MY_DEPT.LAST()) -- Add at The tail of the list.
        FROM
            DEPARTMENTS
        WHERE
            DEPARTMENT_ID = LOOP_COUNT;
        LOOP_COUNT := LOOP_COUNT + 10;
        EXIT WHEN LOOP_COUNT > 100;
    END LOOP;

    LOOP_COUNT := 1;
    FOR LOOP_COUNT IN 1..MY_DEPT.COUNT() LOOP -- Now you have indexes that you can loop over sequentially.
        DBMS_OUTPUT.PUT_LINE(MY_DEPT(LOOP_COUNT));
    END LOOP;
END;
/

--Q2**:
DECLARE
    TYPE DEP_TABLE IS
        VARRAY(10) OF DEPARTMENTS.DEPARTMENT_NAME%TYPE; -- Varray = Upper limit for size.
    MY_DEPT    DEP_TABLE := DEP_TABLE(); -- You also have to Initialize.
    LOOP_COUNT NUMBER := 10;
BEGIN
    LOOP
        MY_DEPT.EXTEND; -- Same thing with the list.
        SELECT
            DEPARTMENT_NAME INTO MY_DEPT(MY_DEPT.LAST())
        FROM
            DEPARTMENTS
        WHERE
            DEPARTMENT_ID = LOOP_COUNT;
        LOOP_COUNT := LOOP_COUNT + 10;
        EXIT WHEN LOOP_COUNT > 100;
    END LOOP;

    LOOP_COUNT := 1;
    FOR LOOP_COUNT IN 1..MY_DEPT.COUNT() LOOP -- Same idea.
        DBMS_OUTPUT.PUT_LINE(MY_DEPT(LOOP_COUNT));
    END LOOP;
END;
/

--Q3:
DECLARE
    TYPE DEP_TABLE IS
        TABLE OF DEPARTMENTS%ROWTYPE INDEX BY PLS_INTEGER;
    MY_DEPT    DEP_TABLE;
    LOOP_COUNT NUMBER := 10;
BEGIN
    LOOP
        SELECT
            * INTO MY_DEPT(LOOP_COUNT)
        FROM
            DEPARTMENTS
        WHERE
            DEPARTMENT_ID = LOOP_COUNT;
        DBMS_OUTPUT.PUT_LINE('Department Number: '
                             ||MY_DEPT(LOOP_COUNT).DEPARTMENT_ID
                             || ' Department Name: '
                             ||MY_DEPT(LOOP_COUNT).DEPARTMENT_NAME
                             ||' Manager Id: '
                             || MY_DEPT(LOOP_COUNT).MANAGER_ID
                             ||' Location Id: '
                             ||MY_DEPT(LOOP_COUNT).LOCATION_ID);
        LOOP_COUNT := LOOP_COUNT + 10;
        EXIT WHEN LOOP_COUNT > 100;
    END LOOP;
END;
 -- From Group.
/

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
        DBMS_OUTPUT.PUT_LINE('Prime');
    ELSIF FLAG = 1 THEN
        DBMS_OUTPUT.PUT_LINE('Not Prime');
    END IF;
END;
/
