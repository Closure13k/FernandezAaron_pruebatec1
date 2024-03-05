package com.closure13k.aaronfmpt1.persistence;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.logic.exceptions.EmployeeException;
import com.closure13k.aaronfmpt1.persistence.exceptions.NonexistentEntityException;
import com.closure13k.aaronfmpt1.persistence.exceptions.PreexistingEntityException;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.*;
import java.util.stream.Collectors;

public class PersistenceController {
    EmployeeJpaController empJpaCont = new EmployeeJpaController();


    /**
     * Se encarga de crear empleados en lote.<p>
     * Primero busca los empleados que ya existen en la base de datos según los nifs del lote.
     * Luego los separa en dos listas, una para los activos y otra para los inactivos.<p>
     * Los inactivos los actualiza y los activos los elimina de la lista original.
     * Finalmente, si quedan empleados en la lista original, los crea.<p>
     * Si quedan empleados activos en la lista de existentes, lanza una excepción con los nifs.
     *
     * @param employees Lista de empleados a registrar.
     * @throws EmployeeException Si hay empleados con nifs ya existentes y activos.
     */
    public void batchCreateEmployees(List<Employee> employees) throws EmployeeException {
        List<String> nifs = employees.stream().map(Employee::getNif).toList();
        List<Employee> existingEmployees = empJpaCont.findEmployeesByListOfNifs(nifs);
        /*
        Ahora filtramos para diferenciar los activos de los inactivos. Con un mapa mismo.
        Lo hice así para que sea más fácil de leer. Soy consciente de que lo evaluado es
        cómo usamos bucles y demás.
        */
        Map<Boolean, List<Employee>> existingEmployeesMap = existingEmployees.stream()
                .collect(Collectors.partitioningBy(Employee::isActive));

        //Empezamos con los inactivos.
        for (Employee inactiveEmployee : existingEmployeesMap.get(false)) {
            //Cogemos de la lista original el empleado con el mismo nif.
            Employee existingEmployee = employees.stream()
                    .filter(e -> e.getNif().equals(inactiveEmployee.getNif()))
                    .findFirst()
                    .orElseThrow(); // No debería ocurrir.

            inactiveEmployee.setActive(true);
            inactiveEmployee.setName(existingEmployee.getName());
            inactiveEmployee.setSurname(existingEmployee.getSurname());
            inactiveEmployee.setSalary(existingEmployee.getSalary());
            inactiveEmployee.setRole(existingEmployee.getRole());
            inactiveEmployee.setHireDate(existingEmployee.getHireDate());

            updateEmployee(inactiveEmployee);
            employees.remove(existingEmployee);
        }
        //Ahora para los restantes. Los cargaremos en una excepción.
        //Por ahora vaciaremos la lista original para que sólo queden los nuevos.
        for (Employee activeEmployee : existingEmployeesMap.get(true)) {
            employees.removeIf(e -> e.getNif().equals(activeEmployee.getNif()));
        }
        if (!employees.isEmpty()) {
            for (Employee employee : employees) {
                createEmployee(employee);
            }
        }

        if (existingEmployeesMap.get(true).isEmpty()) {
            return;
        }
        throw new EmployeeException(
                "Algunos NIF del lote ya existen en la base de datos."
                        + existingEmployeesMap.get(true).stream()
                        .map(Employee::getNif)
                        .collect(Collectors.joining(", "))
        );

    }

    public void createEmployee(Employee employee) {
        try {
            empJpaCont.create(employee);
        } catch (RollbackException e) {
            System.out.println("petó aquí" + getClass().getSimpleName() + e.getClass());
        } catch (PreexistingEntityException e) {
            throw new RuntimeException(e);
        }

    }

    public Employee findEmployeeById(int id) {
        return empJpaCont.findEmployee(id);
    }

    public void deleteEmployee(Employee employee) {
        if (!employee.isActive()) {
            return;
        }
        try {
            employee.setActive(false);
            empJpaCont.edit(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public List<Employee> listAllActiveEmployees() {
        return empJpaCont.findActiveEmployeeEntities();
    }


    public List<Employee> listAllActiveEmployeesByRole(String role) {
        return empJpaCont.findActiveEmployeeEntitiesByRole(role);
    }


    public void updateEmployee(Employee employee) {
        try {
            empJpaCont.edit(employee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Employee findEmployeeByNif(String nif) {
        try {
            return empJpaCont.findEmployee(nif);
        } catch (NoResultException e) {
            return null;
        }
    }
}
