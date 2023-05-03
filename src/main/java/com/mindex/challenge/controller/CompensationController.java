package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Compensation API", description = "Compensation data related to Employee")
public class CompensationController {
  private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);
  @Autowired private CompensationService compensationService;

  /**
   * POST request to create a Compensation object for an Employee
   *
   * @param compensation - Compensation Request Body
   * @return Compensation
   */
  @Operation(summary = "Create Employee Compensation")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created Employee Compensation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Compensation.class))
            })
      })
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
  @Operation(summary = "Get Employee Compensation by Employee Id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found Employee Compensation",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Compensation.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Employee Compensation not found",
            content = @Content)
      })
  @GetMapping("/compensation/{employeeId}")
  public Compensation create(@PathVariable String employeeId) {
    LOG.debug("GET /compensation/{employeeId} for employee id [{}]", employeeId);

    return compensationService.read(employeeId);
  }
}
