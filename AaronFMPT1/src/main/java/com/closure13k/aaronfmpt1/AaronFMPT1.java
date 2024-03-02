package com.closure13k.aaronfmpt1;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.persistence.PersistenceController;
import java.time.LocalDate;

public class AaronFMPT1 {

    public static void main(String[] args) {
        PersistenceController pc = new PersistenceController();
        pc.createEmployee(new Employee("1239123", "Pepo", "Martínez López", "Responsable de Centro", 2250d, LocalDate.now()));
    }
}
