package com.mindex.challenge;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ChallengeApplicationTests {

  @Autowired private EmployeeService employeeService;
  @Autowired private ReportingStructureService reportingStructureService;
  @Autowired private CompensationService compensationService;

  @Autowired private EmployeeRepository employeeRepository;

  @Autowired private CompensationRepository compensationRepository;

  @Test
  public void contextLoads() {
    assertThat(employeeService).isNotNull();
    assertThat(reportingStructureService).isNotNull();
    assertThat(compensationService).isNotNull();

    assertThat(employeeRepository).isNotNull();
    assertThat(compensationRepository).isNotNull();
  }
}
