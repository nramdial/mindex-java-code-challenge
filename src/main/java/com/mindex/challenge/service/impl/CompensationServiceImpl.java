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
   * Creates a Compensation object for Employee.`
   *
   * @param compensation - Compensation object composed of Employee, salary, and effectiveStartDate
   * @return Compensation
   */
  @Override
  public Compensation create(Compensation compensation) {
    LOG.debug("Creating compensation for employee [{}]", compensation);

    Employee employee = employeeService.read(compensation.getEmployee().getEmployeeId());
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
