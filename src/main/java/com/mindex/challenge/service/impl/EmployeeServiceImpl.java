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

    /**
     * Create Employee record
     * @param employee - new Employee record
     * @return Employee
     */
  @Override
  public Employee create(Employee employee) {
    LOG.debug("Creating employee [{}]", employee);

    employee.setEmployeeId(UUID.randomUUID().toString());
    employeeRepository.insert(employee);

    return employee;
  }

    /**
     * Read Employee record
     * @param id - Employee Id
     * @return Employee
     */
  @Override
  public Employee read(String id) {
    LOG.debug("Creating employee with id [{}]", id);

    Employee employee = employeeRepository.findByEmployeeId(id);

    if (employee == null) {
      throw new RuntimeException("Invalid employeeId: " + id);
    }

    if (employee.getDirectReports() != null) {
      // Update employee in-place for directReports with recursive method
      retrieveFullEmployeeData(employee, employee.getDirectReports());
    }

    return employee;
  }

    /**
     * Update existing Employee record
     * @param employee - Employee record to update with
     * @return Employee
     */
  @Override
  public Employee update(Employee employee) {
    LOG.debug("Updating employee [{}]", employee);

    return employeeRepository.save(employee);
  }

  /**
   * Given an employee with a direct report array, enrich the contents of the direct report array
   * for each direct report's employee dataset
   *
   * @param employee - Employee to add full employee direct report dataset
   * @param directReports - List of direct reports we need to enrich with employee data
   */
  private void retrieveFullEmployeeData(Employee employee, List<Employee> directReports) {
    List<Employee> updatedDirectReports = new ArrayList<>();

    for (Employee directReport : directReports) {
      Employee fullEmployeeData = employeeRepository.findByEmployeeId(directReport.getEmployeeId());
      if (fullEmployeeData.getDirectReports() != null) {
        // Update fullEmployeeData in-place for directReports
        retrieveFullEmployeeData(fullEmployeeData, fullEmployeeData.getDirectReports());
      }

      updatedDirectReports.add(fullEmployeeData);
    }
    employee.setDirectReports(updatedDirectReports);
  }
}
