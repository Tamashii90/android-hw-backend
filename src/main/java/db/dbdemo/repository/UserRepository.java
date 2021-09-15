package db.dbdemo.repository;

import db.dbdemo.model.MyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<MyUser, String> {
    Optional<MyUser> findByEmail(String email);
    boolean existsByEmail(String email);
}
