package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Stack;

@Service
public class ReportingStructureImpl implements ReportingStructureService {

  @Autowired private EmployeeService employeeService;

  /**
   * Retrieves all reports of given Employee, both direct and nested reports
   *
   * @param employeeId - UUID string of Employee
   * @return Employee
   */
  @Override
  public ReportingStructure read(String employeeId) {
    ReportingStructure response = new ReportingStructure();
    Employee employee = employeeService.read(employeeId);

    response.setEmployee(employee);

    int numOfReports = 0;

    // Keep adding all direct reports and their direct reports to stack while incrementing until we
    // exhaust list
    Stack<Employee> stack = new Stack<>();
    stack.addAll(employee.getDirectReports());
    while (!stack.isEmpty()) {
      Employee directReport = stack.pop();
      numOfReports++;
      if (!directReport.getDirectReports().isEmpty()) {
        stack.addAll(directReport.getDirectReports());
      }
    }

    response.setNumberOfReports(numOfReports);
    return response;
  }
}
