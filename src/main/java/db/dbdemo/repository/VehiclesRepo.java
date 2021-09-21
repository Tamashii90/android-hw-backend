package db.dbdemo.repository;

import db.dbdemo.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiclesRepo extends CrudRepository<Vehicle, String> {
    Optional<Vehicle> findByDriver(String driver);
    boolean existsByDriver(String driver);
}
