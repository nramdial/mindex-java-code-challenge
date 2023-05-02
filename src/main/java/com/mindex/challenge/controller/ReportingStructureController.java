package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

@RestController
public class ReportingStructureController {

  @Autowired private ReportingStructureService reportingStructureService;

  private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

  /**
   * GET Reporting structure by Employee
   *
   * @param employeeId - Employee Id
   * @return ReportingStructure
   */
  @GetMapping("/reporting-structure/{employeeId}")
  public ReportingStructure read(
          @PathVariable
          String employeeId) {
    LOG.debug("GET /reporting-structure/{employeeId} [{}]", employeeId);

    return reportingStructureService.read(employeeId);
  }
}
