package db.dbdemo.repository;

import db.dbdemo.model.Admin;
import org.springframework.data.repository.CrudRepository;

public interface AdminsRepo extends CrudRepository<Admin, String> {
    boolean existsByUsername(String username);
}
