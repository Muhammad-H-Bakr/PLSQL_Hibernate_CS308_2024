-- Create a table:
BEGIN
    EXECUTE IMMEDIATE 'create table temp (
id number primary key
)';
END;
/

-- Insert a value initially into the table:
BEGIN
    INSERT INTO TEMP VALUES (
        0
    );
END;
/

-- Create a procedure that inserts into it:
CREATE OR REPLACE PROCEDURE INSERT_TEMP AS
    MAXNUM NUMBER;
BEGIN
    SELECT
        MAX(ID) INTO MAXNUM
    FROM
        TEMP;
    INSERT INTO TEMP VALUES (
        MAXNUM+1
    );
END;
/

-- Create a program that utilizes the procedure:
BEGIN
    DBMS_SCHEDULER.CREATE_PROGRAM(
        PROGRAM_NAME => 'inset_into_temp',
        PROGRAM_TYPE => 'Stored_procedure',
        PROGRAM_ACTION => 'INSERT_TEMP',
        ENABLED => TRUE
    );
END;
/

-- Create a scheduler:
BEGIN
    DBMS_SCHEDULER.CREATE_SCHEDULE(
        SCHEDULE_NAME => 'Temp_schedule',
        START_DATE => SYSTIMESTAMP,
        REPEAT_INTERVAL => 'Freq = secondly; interval = 5'
    );
END;
/

-- Link the program and the scheduler to a job:
BEGIN
    DBMS_SCHEDULER.CREATE_JOB(
        JOB_NAME => 'Test_Temp',
        PROGRAM_NAME => 'inset_into_temp',
        SCHEDULE_NAME =>'Temp_schedule',
        ENABLED => TRUE
    );
END;
/

-- Test:
SELECT
    *
FROM
    TEMP;

-- Undo what you have done:
BEGIN
    DBMS_SCHEDULER.DROP_JOB(
        JOB_NAME =>'Test_temp'
    );
    DBMS_SCHEDULER.DROP_PROGRAM(
        PROGRAM_NAME =>'inset_into_temp'
    );
    DBMS_SCHEDULER.DROP_SCHEDULE(
        SCHEDULE_NAME =>'Temp_schedule'
    );
END;
/

DROP TABLE TEMP;