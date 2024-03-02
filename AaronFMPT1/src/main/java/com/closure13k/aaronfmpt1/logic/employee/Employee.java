package com.closure13k.aaronfmpt1.logic.employee;

import java.io.Serializable;
import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Empleados")
@NamedQueries({
        @NamedQuery(name = "Employee.findAllActiveEmployees", query = "SELECT e FROM Empleados e WHERE e.active <> false"),
        @NamedQuery(name = "Employee.findByNif", query = "SELECT e FROM Empleados e WHERE e.active <> false AND e.nif = :nif"),
        @NamedQuery(name = "Employee.findAllByRole", query = "SELECT e FROM Empleados e WHERE e.active <> false AND e.role = :role")
})
@Table(uniqueConstraints = {
        //Alter table add
        @UniqueConstraint(name = "UQ_Empleados_DniNif", columnNames = {"dni_nif"})
})
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private int id;
    /*
      !TODO: Necesita un validador de DNI en la clase controladora de Empleado.
     */
    @Column(name = "dni_nif",
            //ColumnDefinition sobreescribe opciones como nullable
            columnDefinition = "CHAR(9) NOT NULL")
    private String nif;

    @Column(name = "nombre",
            nullable = false,
            length = 50)
    private String name;

    @Column(name = "apellido",
            nullable = false,
            length = 50)
    private String surname;

    @Column(name = "cargo",
            nullable = false,
            length = 50)
    private String role;

    @Column(name = "salario",
            nullable = false,
            precision = 10,
            scale = 2)
    private Double salary;

    @Column(name = "fecha_contratacion",
            nullable = false)
    private LocalDate hireDate;

    @Column(name = "cuenta_activa",
            nullable = false)
    private Boolean active;

    public Employee() {
    }

    public Employee(String nif, String name, String surname, String role,
                    Double salary, LocalDate hireDate) {
        this.nif = nif;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.salary = salary;
        this.hireDate = hireDate;
        this.active = Boolean.TRUE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFormattedDetails() {
        return "ID: " + id + "\n"
                + "NIF: " + nif + "\n"
                + "Nombre: " + name + "\n"
                + "Apellido: " + surname + "\n"
                + "Cargo: " + role + "\n"
                + "Salario: " + salary + "\n"
                + "Fecha de contratación: " + hireDate;
    }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", nif='" + nif + '\''
                + ", name='" + name + '\''
                + ", surname='" + surname + '\''
                + ", role='" + role + '\''
                + ", salary=" + salary
                + ", hireDate=" + hireDate
                + ", active=" + active.toString()
                + '}';
    }
}
