package db.dbdemo.repository;

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
public interface ViolationsLogRepo extends CrudRepository<ViolationLog, Long> {

    String FIND_VIOLATION_CARDS_QUERY = "SELECT `violations_log`.`id`, `vehicles`.`plate_number`, `vehicles`.`driver`, `violations_log`.`location`, `violations_log`.`date`, `violations_log`.`paid`, `violations`.`tax`, `violations`.`type` FROM \n" +
            "((`violations_log` INNER JOIN `vehicles` ON `violations_log`.`plate_number` = `vehicles`.`plate_number`) INNER JOIN `violations` ON violation_type = `violations`.`type`) WHERE \n" +
            "`vehicles`.`cross_out` = 0 AND \n" +
            "`vehicles`.`plate_number` LIKE IFNULL(:plateNumber, '%') AND \n" +
            "`vehicles`.`driver` LIKE IFNULL(:driver, '%') AND \n" +
            "`violations_log`.`location` LIKE IFNULL(:location, '%') AND \n" +
            "`violations_log`.`date` BETWEEN IFNULL(:fromDate, '1900-01-01') AND IFNULL(:toDate, CURRENT_DATE()) ORDER BY id DESC";
    String FIND_VIOLATION_CARD_QUERY = "SELECT `violations_log`.`id`, `vehicles`.`plate_number`, `vehicles`.`driver`, `violations_log`.`location`, `violations_log`.`date`, `violations_log`.`paid`, `violations`.`tax`, `violations`.`type` FROM \n" +
            "((`violations_log` INNER JOIN `vehicles` ON `violations_log`.`plate_number` = `vehicles`.`plate_number`) INNER JOIN `violations` ON violation_type = `violations`.`type`) WHERE \n" +
            "`violations_log`.`id`=:id";
    String FIND_USERS_VIOLATION_CARDS_QUERY = "SELECT `violations_log`.`id`, `vehicles`.`plate_number`, `vehicles`.`driver`, `violations_log`.`location`, `violations_log`.`date`, `violations_log`.`paid`, `violations`.`tax`, `violations`.`type` FROM \n" +
            "((`violations_log` INNER JOIN `vehicles` ON `violations_log`.`plate_number` = `vehicles`.`plate_number`) INNER JOIN `violations` ON violation_type = `violations`.`type`) WHERE \n" +
            "`vehicles`.`cross_out` = 0 AND \n" +
            "`violations_log`.`paid` = 0 AND \n" +
            "`vehicles`.`plate_number` = :plateNumber AND \n" +
            "`violations_log`.`location` LIKE IFNULL(:location, '%') AND \n" +
            "`violations_log`.`date` BETWEEN IFNULL(:fromDate, '1900-01-01') AND IFNULL(:toDate, CURRENT_DATE()) ORDER BY id DESC";
    String UPDATE_VIOLATION_LOG_QUERY = "UPDATE `violations_log`\n" +
            "SET `date` = :date, `violation_type` = :type,\n" +
            "`location` = :location, `paid` = :paid WHERE `id` = :id";
    String PAY_FOR_VIOLATION_QUERY = "UPDATE `violations_log`\n" +
            "SET `paid` = 1 WHERE `id` = :id";


    @Transactional
    @Modifying
    @Query(value = "INSERT INTO violations_log(plate_number, violation_type, date, location, paid) VALUES(:plateNumber, :violationType, CURRENT_DATE, :location, :paid)", nativeQuery = true)
    void insertLog(@Param("plateNumber") String plateNumber, @Param("violationType") String violationType,
                   @Param("location") String location, @Param("paid") boolean paid);

    @Query(value = FIND_VIOLATION_CARDS_QUERY, nativeQuery = true)
    List<ViolationCard> getViolationCards(
            @Param("plateNumber") String plateNumber,
            @Param("driver") String driver,
            @Param("location") String location,
            @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate
    );

    @Query(value = FIND_USERS_VIOLATION_CARDS_QUERY, nativeQuery = true)
    List<ViolationCard> getUsersViolationCards(
            @Param("plateNumber") String plateNumber,
            @Param("location") String location,
            @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate
    );

    @Query(value = FIND_VIOLATION_CARD_QUERY, nativeQuery = true)
    ViolationCard findViolationCard(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = UPDATE_VIOLATION_LOG_QUERY, nativeQuery = true)
    void updateViolationLog(
            @Param("id") Long id,
            @Param("date") LocalDate date,
            @Param("type") String type,
            @Param("location") String location,
            @Param("paid") boolean paid
    );

    @Transactional
    @Modifying
    @Query(value = PAY_FOR_VIOLATION_QUERY, nativeQuery = true)
    void payForViolation(@Param("id") Long id);
}
