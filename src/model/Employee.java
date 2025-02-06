/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Logable;

/**
 *
 * @author YixinHuang
 */
public class Employee extends Person implements Logable {

    private int employeeid;
    private String password;
    
    final int EMPLOYEE_ID = 123;
    final String PASSWORD = "test";

    public Employee(String nombre) {
        super(nombre);
    }

    public Employee(int employeeid, String password) {
        this.employeeid = employeeid;
        this.password = password;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    

    @Override
    public boolean login(int user, String password) {
        if (user == this.EMPLOYEE_ID && password.equalsIgnoreCase(this.PASSWORD)) {
            return true;
        } else {
            System.out.println("Datos incorrectos");
            return false;
        }
    }

}
