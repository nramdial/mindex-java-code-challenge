package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
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
@Tag(name = "Employee API", description = "Create,Read,Update Employee")
public class EmployeeController {
  private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

  @Autowired private EmployeeService employeeService;

  @Operation(summary = "Create Employee")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Created Employee",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Employee.class))
            })
      })
  @PostMapping("/employee")
  public Employee create(@RequestBody Employee employee) {
    LOG.debug("Received employee create request for [{}]", employee);

    return employeeService.create(employee);
  }

  @Operation(summary = "Get Employee by Employee Id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Found Employee",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Employee.class))
            }),
        @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
      })
  @GetMapping("/employee/{id}")
  public Employee read(@PathVariable String id) {
    LOG.debug("Received employee create request for id [{}]", id);

    return employeeService.read(id);
  }

  @Operation(summary = "Update Employee by Employee Id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Updated Employee",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = Employee.class))
            }),
        @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
      })
  @PutMapping("/employee/{id}")
  public Employee update(@PathVariable String id, @RequestBody Employee employee) {
    LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

    employee.setEmployeeId(id);
    return employeeService.update(employee);
  }
}
