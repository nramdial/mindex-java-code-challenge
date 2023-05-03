package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {
  private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
  @Autowired private CompensationService compensationService;

  /**
   * POST request to create a Compensation object for an Employee
   *
   * @param compensation - Compensation Request Body
   * @return Compensation
   */
  @PostMapping("/compensation")
  public Compensation create(@RequestBody Compensation compensation) {
    LOG.debug("POST /compensation [{}]", compensation);

    return compensationService.create(compensation);
  }

  /**
   * GET Compensation by Employee Id
   *
   * @param employeeId - Employee Id
   * @return Compensation
   */
  @GetMapping("/compensation/{employeeId}")
  public Compensation create(@PathVariable String employeeId) {
    LOG.debug("GET /compensation/{employeeId} for employee id [{}]", employeeId);

    return compensationService.read(employeeId);
  }
}
