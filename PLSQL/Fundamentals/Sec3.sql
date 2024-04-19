--Q1:
DECLARE
     COUNTRY_RECORD COUNTRIES%ROWTYPE;
     COUNTRYID      COUNTRIES.COUNTRY_ID%TYPE := 'CA';
BEGIN
     SELECT
          * INTO COUNTRY_RECORD
     FROM
          COUNTRIES
     WHERE
          COUNTRY_ID = COUNTRYID;
     DBMS_OUTPUT.PUT_LINE('country Id: '
                          || COUNTRY_RECORD.COUNTRY_ID
                          || ' Country Name: '
                          || COUNTRY_RECORD.COUNTRY_NAME
                          ||' Region: '
                          || COUNTRY_RECORD.REGION_ID);
END;
 --Q2:
DECLARE
     TYPE HELPER IS
          TABLE OF DEPARTMENTS.DEPARTMENT_NAME % TYPE INDEX BY PLS_INTEGER;
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