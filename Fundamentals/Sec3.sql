--Q1:
declare 
      country_record countries%rowtype;
      countryid countries.country_id%type := 'CA';
begin
     select *
     into country_record
     from countries
     where country_id = countryid;
     DBMS_OUTPUT.put_line('country Id: '|| country_record.country_id || ' Country Name: '||
     country_record.country_name ||' Region: ' || country_record.region_id);
end;

--Q2:
DECLARE
     TYPE HELPER IS
          TABLE OF DEPARTMENTS.DEPARTMENT_NAME % TYPE
           INDEX BY PLS_INTEGER;
     ANS HELPER;
     I   NUMBER := 10;
BEGIN
     LOOP
          SELECT
               DEPARTMENT_NAME INTO ANS(I)
          FROM
               DEPARTMENTS
          WHERE
               DEPARTMENT_ID = I;
          DBMS_OUTPUT.PUT_LINE(ANS(I));
          I:= I+ 10;
          EXIT WHEN I > 50;
     END LOOP;
END;