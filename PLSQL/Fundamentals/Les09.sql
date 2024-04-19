--Q1:
CREATE OR REPLACE PROCEDURE GREET IS
BEGIN
    DBMS_OUTPUT.PUT_LINE('Hello World');
    DBMS_OUTPUT.PUT_LINE('TODAY IS: '
                         || SYSDATE());
    DBMS_OUTPUT.PUT_LINE('TOMORROW IS: '
                         || (SYSDATE()+1));
END;
/

BEGIN
    GREET;
END;
/

--Q2: Unintentional overloading :)
CREATE OR REPLACE PROCEDURE GREET(
    NAME VARCHAR2
) IS
BEGIN
    DBMS_OUTPUT.PUT_LINE('Hello '
                         ||NAME);
    DBMS_OUTPUT.PUT_LINE('TODAY IS: '
                         || SYSDATE());
    DBMS_OUTPUT.PUT_LINE('TOMORROW IS: '
                         || (SYSDATE()+1));
END;
/

BEGIN
    GREET('&name');
END;
/