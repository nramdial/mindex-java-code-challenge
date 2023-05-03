package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;

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

    List<Employee> currentList = employee.getDirectReports();
    int numOfReports = currentList.size();
    ListIterator<Employee> listItr = currentList.listIterator();

    // Add each nested direct report to our numOfReports sum and continue to iterate
    // until we exhaust all reports with direct reports
    while (listItr.hasNext()) {
      List<Employee> nestedDirectReport = listItr.next().getDirectReports();

      if (nestedDirectReport != null && !nestedDirectReport.isEmpty()) {
        numOfReports += nestedDirectReport.size();
        nestedDirectReport.forEach(listItr::add);
      }
    }

    response.setNumberOfReports(numOfReports);
    return response;
  }
}
