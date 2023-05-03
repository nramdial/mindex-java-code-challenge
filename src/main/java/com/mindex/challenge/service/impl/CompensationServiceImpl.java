package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CompensationServiceImpl implements CompensationService {
  private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
  @Autowired private CompensationRepository compensationRepository;
  @Autowired private EmployeeService employeeService;

  /**
   * Creates a Compensation object for Employee If Employee does not exist, this method will create
   * a new Employee before creating a compensation object
   *
   * @param compensation - Compensation object composed of Employee, salary, and effectiveStartDate
   * @return Compensation
   */
  @Override
  public Compensation create(Compensation compensation) {
    LOG.debug("Creating compensation for employee [{}]", compensation);

    Employee employee = null;

    // Verify if employee with given ID exists
    if (compensation.getEmployee().getEmployeeId() != null) {
      try {
        employee = employeeService.read(compensation.getEmployee().getEmployeeId());
      } catch (RuntimeException e) {
        // Catching RuntimeException from Employee Service
        // Ideally, this would be a custom Exception class we can handle (e.g.
        // CustomNotFoundException.java)
        LOG.error(e.getLocalizedMessage());
      }
    }

    // If Employee not found,
    // create against Employee Service before creating against Compensation service
    if (employee == null) {
      employee = employeeService.create(compensation.getEmployee());
    }
    compensation.setEmployee(employee);
    compensationRepository.insert(compensation);

    return compensation;
  }

  /**
   * Retrieves compensation by employee id
   *
   * @param employeeId - UUID of Employee
   * @return Compensation
   */
  @Override
  public Compensation read(String employeeId) {
    LOG.debug("Retrieving compensation for employee id [{}]", employeeId);

    return compensationRepository
        .findByEmployee_EmployeeId(employeeId)
        .orElseThrow(
            () ->
                new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("Employee with id not found [%s]", employeeId)));
  }
}
