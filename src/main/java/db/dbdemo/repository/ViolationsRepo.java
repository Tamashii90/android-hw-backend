package db.dbdemo.repository;

import db.dbdemo.model.Violation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViolationsRepo extends CrudRepository<Violation, String> {
    @Query(value = "SELECT type from violations", nativeQuery = true)
    List<String> findAllTypes();
}
