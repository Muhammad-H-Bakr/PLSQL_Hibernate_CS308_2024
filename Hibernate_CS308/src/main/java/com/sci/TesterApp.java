package com.sci;

import com.sci.criteria.FilterQuery;
import com.sci.criteria.Operator;
import com.sci.dao.DBConfig;
import com.sci.dao.DBManagement;
import com.sci.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TesterApp {
    public static void main(String[] args) {
        DBManagement.DBEmployee emp = new DBManagement.DBEmployee();
        List<FilterQuery> filters = new ArrayList<>();

        // Q13, Les02 Oracle SQL Slides:
        filters.add(new FilterQuery("jobId", Arrays.asList("SA_REP", "ST_CLERK"),
                Operator.In));
        filters.add(new FilterQuery("salary", Arrays.asList(2500, 3500, 7000),
                Operator.NotIn));
        List<Employee> emps = emp.getByFilter(filters);
        System.out.println("LastName, JobId, Salary");
        for (Employee employee : emps) {
            System.out.println(employee.getLastName() + " " + employee.getJobId() + " "
                    + employee.getSalary());
        }
        DBConfig.shutdown();
    }
}
