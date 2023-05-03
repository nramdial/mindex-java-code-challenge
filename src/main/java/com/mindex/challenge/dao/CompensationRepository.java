package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompensationRepository extends MongoRepository<Compensation, String> {
  // Enable foreign key mapping with naming convention "findBy<Entity>_<ForeignKeyField>"
  Optional<Compensation> findByEmployee_EmployeeId(String employeeId);
}
