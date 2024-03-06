package com.closure13k.aaronfmpt1.persistence;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.logic.employee.exceptions.EmployeeException;
import com.closure13k.aaronfmpt1.persistence.exceptions.PreexistingEntityException;

import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.*;
import java.util.stream.Collectors;

public class PersistenceController {
    EmployeeJpaController empJpaCont = new EmployeeJpaController();

    /**
     * Se encarga de crear empleados en lote.<p>
     * Primero busca los empleados que ya existen en la base de datos según los NIF del lote.
     * Luego los separa en dos listas, una para los activos y otra para los inactivos.<p>
     * Los inactivos los actualiza y los activos los elimina de la lista original.
     * Finalmente, si quedan empleados en la lista original, los crea.<p>
     * Si quedan empleados activos en la lista de existentes, lanza una excepción con los NIF.
     *
     * @param employees Lista de empleados a registrar.
     * @throws EmployeeException Si hubiera empleados con NIF ya existentes y activos.
     */
    public void batchCreateEmployees(List<Employee> employees) throws EmployeeException {
        /*
          Esto sería más cómodo con streams, pero no se evaluaría.
          employees.stream()
                   .map(Employee::getNif).toList();
          existingEmployees.stream()
                           .collect(Collectors.partitioningBy(Employee::isActive));
         */
        //Recogemos los NIF de los empleados.
        List<String> nifList = new ArrayList<>();
        for (Employee employee1 : employees) {
            nifList.add(employee1.getNif());
        }
        //Buscamos los empleados que ya existen en la base de datos con los NIF del lote.
        List<Employee> existingEmployees = empJpaCont.findEmployeesByListOfNifs(nifList);
        //Los separamos en dos listas, una para los activos y otra para los inactivos.
        Map<Boolean, List<Employee>> groupedByStatus = new HashMap<>();
        groupedByStatus.put(false, new ArrayList<>());
        groupedByStatus.put(true, new ArrayList<>());

        for (Employee e : existingEmployees) {
            groupedByStatus.get(e.isActive()).add(e);
        }

        //Empezamos con los inactivos.
        for (Employee inactiveEmployee : groupedByStatus.get(false)) {
            //Cogemos de la lista original el empleado con el mismo nif.
            Employee found = null;
            for (Employee e : employees) {
                if (e.getNif().equals(inactiveEmployee.getNif())) {
                    found = e;
                    break;
                }
            }
            if (found != null) {
                inactiveEmployee.setActive(true);
                inactiveEmployee.setName(found.getName());
                inactiveEmployee.setSurname(found.getSurname());
                inactiveEmployee.setSalary(found.getSalary());
                inactiveEmployee.setRole(found.getRole());
                inactiveEmployee.setHireDate(found.getHireDate());

                updateEmployee(inactiveEmployee);
                employees.remove(found);
            }
        }

        //Ahora para los restantes. Los cargaremos en una excepción.
        //Por ahora vaciaremos la lista original para que únicamente queden los nuevos.
        for (Employee activeEmployee : groupedByStatus.get(true)) {
            employees.removeIf(e -> e.getNif().equals(activeEmployee.getNif()));
        }
        if (!employees.isEmpty()) {
            for (Employee employee : employees) {
                createEmployee(employee);
            }
        }

        if (groupedByStatus.get(true).isEmpty()) {
            return;
        }
        throw new EmployeeException(
                "Algunos NIF del lote ya existen en la base de datos."
                        + groupedByStatus.get(true).stream()
                        .map(Employee::getNif)
                        .collect(Collectors.joining(", "))
        );

    }

    public void createEmployee(Employee employee) throws EmployeeException {
        try {
            empJpaCont.create(employee);
        } catch (RollbackException e) {
            throw new EmployeeException("No se pudo crear al empleado." + e.getMessage());
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
