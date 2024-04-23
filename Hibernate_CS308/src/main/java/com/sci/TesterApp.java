package com.sci;

import com.sci.dao.DBConfig;
import com.sci.dao.DBManagement;
import com.sci.models.*;

import java.util.List;

public class TesterApp {
    public static void main(String[] args) {
        DBManagement.DBPerson emp = new DBManagement.DBPerson();
//        emp.create(new Person(1,"Ahmed"));
//        emp.create(new Person(2,"Mohammed"));
//        emp.delete(2);
        List<Person> employees = emp.get();
        for (Person employee : employees) {
            //WHEN USING BI-DIRECTIONAL RELATIONS: DO NOT PRINT THE WHOLE OBJECT (DEADLOCK).
            System.out.print("Person: "+ employee.getName()+"\nCars: ");
            for(Car car : employee.getCars()) {
                System.out.print(car.getName()+" ");
            }
            System.out.println();
        }

//        DBManagement.DBCar emp = new DBManagement.DBCar();
//        emp.create(new Car(1,"BMW",1));
//        List<Car> employees = emp.get();
//        for (Car employee : employees) {
//            //WHEN USING BI-DIRECTIONAL RELATIONS: DO NOT PRINT THE WHOLE OBJECT (DEADLOCK).
//            System.out.println(employee);
//        }
        DBConfig.shutdown();
    }
}