package com.mindex.challenge.controller;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "Reporting Structure API", description = "Reporting Structure relative to Employee")
public class ReportingStructureController {

  @Autowired private ReportingStructureService reportingStructureService;

  private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

  /**
   * GET Reporting structure by Employee
   *
   * @param employeeId - Employee Id
   * @return ReportingStructure
   */
  @Operation(summary = "Get Employee Reporting Structure by Employee Id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found Reporting Structure",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ReportingStructure.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Employee not found",
            content = @Content)
      })
  @GetMapping("/reporting-structure/{employeeId}")
  public ReportingStructure read(@PathVariable String employeeId) {
    LOG.debug("GET /reporting-structure/{employeeId} [{}]", employeeId);

    return reportingStructureService.read(employeeId);
  }
}
