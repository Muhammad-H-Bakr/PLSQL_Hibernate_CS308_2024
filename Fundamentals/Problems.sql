-- Write a PL/SQL block that checks whether a given number from the user is prime.
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
        DBMS_OUTPUT.PUT_LINE('Prime.');
    ELSIF FLAG = 1 THEN
        DBMS_OUTPUT.PUT_LINE('Not Prime.');
    END IF;
END;
/

/* Write a PL/SQL block that prints the largest and smallest in an array.
type arr is varray(5) of number;
myArr arr := arr(10,4,6,22,11); */
DECLARE
    TYPE ARR IS
        VARRAY(5) OF NUMBER;
    MYARR ARR := ARR(10, 4, 6, 22, 11);
    MN    NUMBER := MYARR(1);
    MX    NUMBER := 0;
BEGIN
    FOR I IN 1..MYARR.COUNT LOOP
        IF MYARR(I) > MX THEN
            MX := MYARR(I);
        END IF;

        IF MYARR(I) < MN THEN
            MN := MYARR(I);
        END IF;
    END LOOP;

    DBMS_OUTPUT.PUT_LINE('Max: '
                         ||MX
                         ||', Min: '
                         ||MN);
END;
/

/* Write a PL/SQL block that prints every number and its frequency in an array.
Eg: type arr is varray(6) of number;
myArr arr := arr(10,10,10,6,6,11); */
DECLARE
    TYPE ARR IS
        VARRAY(6) OF NUMBER;
    MYARR   ARR := ARR(10, 10, 10, 6, 6, 11);
    TYPE HELPER IS
        TABLE OF NUMBER INDEX BY PLS_INTEGER;
    FREQ    HELPER;
    COUNTER NUMBER;
BEGIN
    FOR I IN 1..MYARR.COUNT LOOP
        IF NOT (FREQ.EXISTS(MYARR(I))) THEN
            FREQ(MYARR(I)) := 1;
        ELSE
            FREQ(MYARR(I)) := FREQ(MYARR(I)) + 1;
        END IF;
    END LOOP;

    FOR I IN FREQ.FIRST .. FREQ.LAST LOOP
        IF FREQ.EXISTS(I) THEN
            IF FREQ(I) = 1 THEN
                DBMS_OUTPUT.PUT_LINE('The number '
                                     ||I
                                     ||' appears one time.');
            ELSE
                DBMS_OUTPUT.PUT_LINE('The number '
                                     ||I
                                     ||' appears '
                                     ||FREQ(I)
                                     ||' times.');
            END IF;
        END IF;
    END LOOP;
END;
/