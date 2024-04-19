/*Write a PL/SQL anonymous block that creates a table named tempTable with attributes: 
id a number that is the primary key
name varchar2 which is not null
salary number that has to be greater than zero.
*/
BEGIN
     EXECUTE IMMEDIATE '
    create table tempTable(
    id number primary key,
    name varchar2(20) not null,
    salary number check (salary > 0)
    )
    ';
END;
 -- Now enter 2 recodrs and presist the data:
BEGIN
     INSERT INTO TEMPTABLE VALUES(
          1,
          'Bakr',
          20000
     );
     INSERT INTO TEMPTABLE VALUES(
          2,
          'M7md',
          30000
     );
     COMMIT;
END;
 -- A query to test each step.
SELECT
     *
FROM
     TEMPTABLE;
 -- Now delete those 2 records permanently:
BEGIN
     EXECUTE IMMEDIATE'
     truncate table tempTable
     ';
END;