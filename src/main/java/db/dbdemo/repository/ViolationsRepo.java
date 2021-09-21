package db.dbdemo.repository;

import db.dbdemo.model.Violation;
import org.springframework.data.repository.CrudRepository;

public interface ViolationsRepo extends CrudRepository<Violation, Long> {
}
