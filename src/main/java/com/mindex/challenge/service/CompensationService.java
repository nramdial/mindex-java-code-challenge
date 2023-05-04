package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {

  /**
   * Creates a Compensation object for Employee. If Employee does not exist, this method will create
   * a new Employee before creating a compensation object
   *
   * @param compensation - Compensation object composed of Employee, salary, and effectiveStartDate
   * @return Compensation
   */
  Compensation create(Compensation compensation);

  /**
   * Retrieves compensation by employee id
   *
   * @param employeeId - UUID of Employee
   * @return Compensation
   */
  Compensation read(String employeeId);
}
