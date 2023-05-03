package com.mindex.challenge.service;

import com.mindex.challenge.data.ReportingStructure;

public interface ReportingStructureService {

  /**
   * Retrieves all reports of given Employee, both direct and nested reports
   *
   * @param employeeId - UUID string of Employee
   * @return Employee
   */
  ReportingStructure read(String employeeId);
}
