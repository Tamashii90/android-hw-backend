package db.dbdemo.repository;

import db.dbdemo.model.VehicleViolationKey;
import db.dbdemo.model.ViolationLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ViolationsLogRepo extends CrudRepository<ViolationLog, VehicleViolationKey> {
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO violations_log(pluged_number, violation_id, date, location, paid) VALUES(:plugedNumber, :violationId, CURRENT_DATE, :location, :paid)", nativeQuery = true)
    void insertLog(@Param("plugedNumber") String plugedNumber, @Param("violationId") long violationId,
                   @Param("location") String location, @Param("paid") boolean paid);

    @Query(value = "SELECT * FROM violations_log WHERE pluged_number = :plugedNumber", nativeQuery = true)
    List<ViolationLog> findViolationLogByPlugedNum(@Param("plugedNumber") String plugedNum);
}
