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

    // As we iterate over each direct report, if there exists "nested" direct reports under the
    // initial list, we add each found "nested" direct report to our ListIterator
    while (listItr.hasNext()) {
      Employee nestedDirectReport = employeeService.read(listItr.next().getEmployeeId());

      if (nestedDirectReport.getDirectReports() != null
          && !nestedDirectReport.getDirectReports().isEmpty()) {
        numOfReports += nestedDirectReport.getDirectReports().size();
        nestedDirectReport.getDirectReports().forEach(listItr::add);
      }
    }

    response.setNumberOfReports(numOfReports);
    return response;
  }
}
