package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

  private String reportingStructureWithIdUrl;

  @Autowired private ReportingStructureService reportingStructureService;

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Before
  public void setup() {
    reportingStructureWithIdUrl = "http://localhost:" + port + "/reporting-structure/{employeeId}";
  }

  @Test
  public void testRead() {
    Employee testEmployee = new Employee();
    testEmployee.setEmployeeId("16a596ae-edd3-4847-99fe-c4518e82c86f");
    testEmployee.setFirstName("John");
    testEmployee.setLastName("Lennon");
    testEmployee.setDepartment("Engineering");
    testEmployee.setPosition("Development Manager");

    ReportingStructure testReportingStructure = new ReportingStructure();
    testReportingStructure.setEmployee(testEmployee);
    testReportingStructure.setNumberOfReports(4);

    // Read checks
    ReportingStructure readReportingStructure =
        restTemplate
            .getForEntity(
                reportingStructureWithIdUrl, ReportingStructure.class, testEmployee.getEmployeeId())
            .getBody();

    assert readReportingStructure != null;
    assertNotNull(readReportingStructure.getEmployee().getEmployeeId());
    assertEquals(
        testReportingStructure.getNumberOfReports(), readReportingStructure.getNumberOfReports());
    assertEmployeeEquivalence(readReportingStructure.getEmployee(), testEmployee);
  }

  private static void assertEmployeeEquivalence(Employee expected, Employee actual) {
    assertEquals(expected.getFirstName(), actual.getFirstName());
    assertEquals(expected.getLastName(), actual.getLastName());
    assertEquals(expected.getDepartment(), actual.getDepartment());
    assertEquals(expected.getPosition(), actual.getPosition());
  }
}
