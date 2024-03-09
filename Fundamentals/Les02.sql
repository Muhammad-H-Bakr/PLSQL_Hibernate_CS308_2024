--Must run one time then execute anything you want.
set serveroutput on;

--Q4:
declare 
today date := sysdate;
tomorrow date;
begin
tomorrow := today +1;
dbms_output.put_line('Hello World');
dbms_output.put_line('TODAY IS '||today);
dbms_output.put_line('TOMORROW IS ' ||tomorrow);
end;
/

--Q5:
variable basic_percent number;
variable pf_percent number;
declare 
today date := sysdate;
tomorrow date;
begin
tomorrow := today +1;
:pf_percent := 12;
dbms_output.put_line('Hello World');
dbms_output.put_line('TODAY IS '||today);
dbms_output.put_line('TOMORROW IS ' ||tomorrow);
end;
/
print basic_percent;
print pf_percent;

