package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        if (employee.getDirectReports() != null) {
            employee = retrieveFullEmployeeData(employee, employee.getDirectReports());
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    private Employee retrieveFullEmployeeData(Employee employee, List<Employee> directReports) {
        List<Employee> updatedDirectReports = new ArrayList<>();

        for (Employee directReport : directReports) {
            Employee fullEmployeeData =
                    employeeRepository.findByEmployeeId(directReport.getEmployeeId());
            if(fullEmployeeData.getDirectReports() != null) {
                // Update fullEmployeeData in-place for directReports
                retrieveFullEmployeeData(fullEmployeeData, fullEmployeeData.getDirectReports());
            }

            updatedDirectReports.add(fullEmployeeData);
        }
        employee.setDirectReports(updatedDirectReports);
        return employee;
    }
}
