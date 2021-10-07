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

    String FIND_VIOLATION_CARDS_QUERY = "SELECT `violations_log`.`id`, `vehicles`.`pluged_number`, `vehicles`.`driver`, `violations_log`.`location`, `violations_log`.`date`, `violations_log`.`paid`, `violations`.`tax`, `violations`.`type` FROM \n" +
            "((`violations_log` INNER JOIN `vehicles` ON `violations_log`.`pluged_number` = `vehicles`.`pluged_number`) INNER JOIN `violations` ON violation_type = `violations`.`type`) WHERE \n" +
            "`vehicles`.`cross_out` = 0 AND \n" +
            "`vehicles`.`pluged_number` LIKE IFNULL(:plugedNumber, '%') AND \n" +
            "`vehicles`.`driver` LIKE IFNULL(:driver, '%') AND \n" +
            "`violations_log`.`location` LIKE IFNULL(:location, '%') AND \n" +
            "`violations_log`.`date` BETWEEN IFNULL(:fromDate, '1900-01-01') AND IFNULL(:toDate, CURRENT_DATE()) ORDER BY id DESC";
    String FIND_VIOLATION_CARD_QUERY = "SELECT `violations_log`.`id`, `vehicles`.`pluged_number`, `vehicles`.`driver`, `violations_log`.`location`, `violations_log`.`date`, `violations_log`.`paid`, `violations`.`tax`, `violations`.`type` FROM \n" +
            "((`violations_log` INNER JOIN `vehicles` ON `violations_log`.`pluged_number` = `vehicles`.`pluged_number`) INNER JOIN `violations` ON violation_type = `violations`.`type`) WHERE \n" +
            "`violations_log`.`id`=:id";
    String FIND_USERS_VIOLATION_CARDS_QUERY = "SELECT `violations_log`.`id`, `vehicles`.`pluged_number`, `vehicles`.`driver`, `violations_log`.`location`, `violations_log`.`date`, `violations_log`.`paid`, `violations`.`tax`, `violations`.`type` FROM \n" +
            "((`violations_log` INNER JOIN `vehicles` ON `violations_log`.`pluged_number` = `vehicles`.`pluged_number`) INNER JOIN `violations` ON violation_type = `violations`.`type`) WHERE \n" +
            "`vehicles`.`cross_out` = 0 AND \n" +
            "`violations_log`.`paid` = 0 AND \n" +
            "`vehicles`.`pluged_number` = :plugedNumber AND \n" +
            "`violations_log`.`location` LIKE IFNULL(:location, '%') AND \n" +
            "`violations_log`.`date` BETWEEN IFNULL(:fromDate, '1900-01-01') AND IFNULL(:toDate, CURRENT_DATE()) ORDER BY id DESC";
    String UPDATE_VIOLATION_LOG_QUERY = "UPDATE `violations_log`\n" +
            "SET `date` = :date, `violation_type` = :type,\n" +
            "`location` = :location, `paid` = :paid WHERE `id` = :id";

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO violations_log(pluged_number, violation_type, date, location, paid) VALUES(:plugedNumber, :violationType, CURRENT_DATE, :location, :paid)", nativeQuery = true)
    void insertLog(@Param("plugedNumber") String plugedNumber, @Param("violationType") String violationType,
                   @Param("location") String location, @Param("paid") boolean paid);

//    @Query(value = "SELECT * FROM violations_log WHERE pluged_number = :plugedNumber", nativeQuery = true)
//    List<ViolationLog> findViolationLogByPlugedNum(@Param("plugedNumber") String plugedNum);

    @Query(value = FIND_VIOLATION_CARDS_QUERY, nativeQuery = true)
    List<ViolationCard> getViolationCards(
            @Param("plugedNumber") String plugedNumber,
            @Param("driver") String driver,
            @Param("location") String location,
            @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate
    );

    @Query(value = FIND_USERS_VIOLATION_CARDS_QUERY, nativeQuery = true)
    List<ViolationCard> getUsersViolationCards(
            @Param("plugedNumber") String plugedNumber,
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
}
