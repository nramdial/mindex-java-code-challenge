package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceImplTest {
  private String createCompensationUrl;
  private String retrieveCompensationByEmployeeIdUrl;

  @Autowired private CompensationService compensationService;
  @Autowired private EmployeeService employeeService;

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Before
  public void setup() {
    createCompensationUrl = "http://localhost:" + port + "/compensation";
    retrieveCompensationByEmployeeIdUrl = "http://localhost:" + port + "/compensation/{employeeId}";
  }

  @Test
  public void testCreateRead() {
    // Verify creation of Compensation with existing Employee
    Employee testEmployee = new Employee();
    testEmployee.setEmployeeId("abc-123");
    testEmployee.setFirstName("John");
    testEmployee.setLastName("Lennon");
    testEmployee.setDepartment("Engineering");
    testEmployee.setPosition("Development Manager");

    Compensation testCompensation = new Compensation();
    testCompensation.setEmployee(testEmployee);
    testCompensation.setSalary(1000.50);
    testCompensation.setEffectiveDate(new Date());

    Compensation createdCompensation =
        restTemplate
            .postForEntity(createCompensationUrl, testCompensation, Compensation.class)
            .getBody();

    assert createdCompensation != null;
    assertNotNull(createdCompensation.getEmployee().getEmployeeId());
    assertCompensationEquivalence(testCompensation, createdCompensation);

    // Verify read of compensation
    Compensation readCompensation =
        restTemplate
            .getForEntity(
                retrieveCompensationByEmployeeIdUrl,
                Compensation.class,
                createdCompensation.getEmployee().getEmployeeId())
            .getBody();

    assert readCompensation != null;
    assertEquals(
        createdCompensation.getEmployee().getEmployeeId(),
        readCompensation.getEmployee().getEmployeeId());
    assertCompensationEquivalence(createdCompensation, readCompensation);
  }

  private static void assertCompensationEquivalence(Compensation expected, Compensation actual) {
    assertEquals(expected.getEmployee().getFirstName(), actual.getEmployee().getFirstName());
    assertEquals(expected.getEmployee().getLastName(), actual.getEmployee().getLastName());
    assertEquals(expected.getEmployee().getDepartment(), actual.getEmployee().getDepartment());
    assertEquals(expected.getEmployee().getPosition(), actual.getEmployee().getPosition());
    assertEquals(expected.getEffectiveDate(), actual.getEffectiveDate());
    assertEquals(expected.getSalary(), actual.getSalary(), 0);
  }
}
