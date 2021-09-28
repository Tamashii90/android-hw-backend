package db.dbdemo.repository;

import db.dbdemo.model.VehicleViolationKey;
import db.dbdemo.model.ViolationCard;
import db.dbdemo.model.ViolationLog;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    @Query(value = "SELECT `vehicles`.`pluged_number`, `vehicles`.`driver`, `violations_log`.`location`, `violations_log`.`date`, `violations_log`.`paid`, `violations`.`tax` FROM \n" +
            "((`violations_log` INNER JOIN `vehicles` ON `violations_log`.`pluged_number` = `vehicles`.`pluged_number`) INNER JOIN `violations` ON violation_id = `violations`.`id`) WHERE \n" +
            "`vehicles`.`cross_out` = 0 AND \n" +
            "`vehicles`.`pluged_number` LIKE IFNULL(:plugedNumber, '%') AND \n" +
            "`vehicles`.`driver` LIKE IFNULL(:driver, '%') AND \n" +
            "`violations_log`.`location` LIKE IFNULL(:location, '%') AND \n" +
            "`violations_log`.`date` BETWEEN IFNULL(:fromDate, '1900-01-01') AND IFNULL(:toDate, CURRENT_DATE())", nativeQuery = true)
    List<ViolationCard> getViolationCards(
            @Param("plugedNumber") String plugedNumber,
            @Param("driver") String driver,
            @Param("location") String location,
            @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate
            );

}
