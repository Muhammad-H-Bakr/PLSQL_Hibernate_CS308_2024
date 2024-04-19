CREATE OR REPLACE PACKAGE PK IS
    X      NUMBER;

    PROCEDURE PROC;

    FUNCTION FUN(
        X NUMBER
    ) RETURN BOOLEAN;
END;
CREATE OR REPLACE PACKAGE BODY PK IS

    PROCEDURE PROC IS
        CURSOR CUR IS (
            SELECT
                FIRST_NAME,
                LAST_NAME
            FROM
                EMPLOYEES
            WHERE
                SALARY > 10000
        );
    BEGIN
        FOR REC IN CUR LOOP
            DBMS_OUTPUT.PUT_LINE(REC.FIRST_NAME
                                 || ' '
                                 || REC.LAST_NAME);
        END LOOP;
    END;

    FUNCTION FUN (
        X NUMBER
    ) RETURN BOOLEAN IS
    BEGIN
        RETURN (X MOD 2 = 0);
    END;
END;
BEGIN
    PK.PROC;
    IF PK.FUN(4) THEN
        DBMS_OUTPUT.PUT_LINE('true');
    ELSE
        DBMS_OUTPUT.PUT_LINE('false');
    END IF;
END;
/