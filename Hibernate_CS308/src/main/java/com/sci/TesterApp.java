package com.sci;

import com.sci.criteria.FilterQuery;
import com.sci.criteria.Operator;
import com.sci.dao.DBConfig;
import com.sci.dao.DBManagement;
import com.sci.models.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class TesterApp {
    public static void main(String[] args) {
        DBManagement.DBEmployee emp = new DBManagement.DBEmployee();
        List<FilterQuery> filters = new ArrayList<>();
        Date hireDate = Date.valueOf("2010-01-01"); //YYYY-MM-DD. (Numeric)
        filters.add(new FilterQuery("salary", 5000,
                Operator.GreaterThan));
        filters.add(new FilterQuery("firstName", "Steven",
                Operator.Equal));
        filters.add(new FilterQuery("hireDate", hireDate,
                Operator.LessThan));
        filters.add(new FilterQuery("jobId", "AD_PRES",
                Operator.Equal));
        List<Employee> emps = emp.getByFilter(filters);
        System.out.println("EmployeeId, FirstName, LastName" );
        for (Employee employee : emps) {
            System.out.println(employee.getEmployeeId() + " " +
                    employee.getFirstName() + " " + employee.getLastName());
        }
        DBConfig.shutdown();
    }
}
