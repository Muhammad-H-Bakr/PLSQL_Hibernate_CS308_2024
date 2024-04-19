--Q4:
DECLARE
    TODAY    DATE := SYSDATE;
    TOMORROW DATE;
BEGIN
    TOMORROW := TODAY +1;
    DBMS_OUTPUT.PUT_LINE('Hello World');
    DBMS_OUTPUT.PUT_LINE('TODAY IS '
                         ||TODAY);
    DBMS_OUTPUT.PUT_LINE('TOMORROW IS '
                         ||TOMORROW);
END;
/

--Q5:
variable basic_percent number;

variable pf_percent number;

DECLARE
    TODAY    DATE := SYSDATE;
    TOMORROW DATE;
BEGIN
    TOMORROW := TODAY +1;
    :PF_PERCENT := 12;
    DBMS_OUTPUT.PUT_LINE('Hello World');
    DBMS_OUTPUT.PUT_LINE('TODAY IS '
                         ||TODAY);
    DBMS_OUTPUT.PUT_LINE('TOMORROW IS '
                         ||TOMORROW);
END;
/

print basic_percent;

print pf_percent;