package com.sci;

import com.sci.criteria.FilterQuery;
import com.sci.criteria.Operator;
import com.sci.dao.DBConfig;
import com.sci.dao.DBManagement;
import com.sci.models.*;
import java.util.ArrayList;
import java.util.List;

public class TesterApp {
    public static void main(String[] args) {
        DBManagement.DBEmployee emp = new DBManagement.DBEmployee();
        List<FilterQuery> filters = new ArrayList<>();
        filters.add(new FilterQuery("lastName", "e",
                Operator.Like));
        filters.add(new FilterQuery("lastName", "a",
                Operator.Like));
        List<Employee> emps = emp.getByFilter(filters);
        System.out.println("LastName");
        for (Employee employee : emps) {
            System.out.println(employee.getLastName());
        }
        DBConfig.shutdown();
    }
}
