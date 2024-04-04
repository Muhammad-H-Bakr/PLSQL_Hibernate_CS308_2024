package com.sci;

import com.sci.dao.DBConfig;
import com.sci.dao.DBManagement;
import com.sci.models.*;
import java.util.List;

public class TesterApp {

  public static void main(String[] args) {
    DBManagement.DBDepartment dbEmployee = new DBManagement.DBDepartment();
    List<Department> employeeList = dbEmployee.get();
    for(Department e : employeeList){
      System.out.println(e);
    }
    Department dept = new Department(280,
            "SCI",null,1700);
    dbEmployee.create(dept);
    System.out.println(dbEmployee.read(280));
    Department dept2 = new Department(280,
            "CS",200,1500);
    dbEmployee.update(dept2);
    System.out.println(dbEmployee.read(280));
    dbEmployee.delete(280);
    System.out.println(dbEmployee.read(280));
    DBConfig.shutdown();
  }
}
