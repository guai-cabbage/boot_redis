package com.shuai.controller;

import com.shuai.bean.Employee;
import com.shuai.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @RequestMapping("/emp/{id}")
    public Employee getEmployee(@PathVariable("id")Integer id){
        return employeeService.getEmp(id);
    }

    @GetMapping("/emp")
    public Employee updateEmployee(Employee employee){
        Employee employee1=employeeService.updateEmp(employee);
        return employee;
    }


    @GetMapping("/delEmp")
    public String deleteEmp(Integer id){
        System.out.println("deleteEmp:"+id);
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastName/{lastName}")
    public Employee getEmployeeByLastName(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }
}
