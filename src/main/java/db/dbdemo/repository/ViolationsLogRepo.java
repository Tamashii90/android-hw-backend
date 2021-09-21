package db.dbdemo.repository;

import db.dbdemo.model.VehicleViolationKey;
import db.dbdemo.model.ViolationLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViolationsLogRepo extends CrudRepository<ViolationLog, VehicleViolationKey> {

}
