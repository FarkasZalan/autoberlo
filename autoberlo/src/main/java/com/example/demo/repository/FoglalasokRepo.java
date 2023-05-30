package com.example.demo.repository;

import com.example.demo.entity.Auto;
import com.example.demo.entity.Foglalasok;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public interface FoglalasokRepo extends JpaRepository<Foglalasok, Integer> {

    @Query(value = "SELECT * FROM `foglalasok` ORDER BY `nyugta_id` DESC;", nativeQuery = true)
    List<Foglalasok> foglalasokListaja();

    @Query(value = "SELECT * FROM `foglalasok` WHERE auto_id = :auto_id", nativeQuery = true)
    List<Foglalasok> aktivfoglalasokListaja(Integer auto_id);

    @Modifying
    @Query(value = "update foglalasok set foglalas_allapota= :allapot where nyugta_id = :nyugta_id", nativeQuery = true)
    void foglalasLemondasa(@Param("nyugta_id") Integer nyugta_id, @Param("allapot") String allapot);

    @Query(value="SELECT COUNT(*) FROM foglalasok WHERE auto_id = :id AND ((`kezdo_datum` BETWEEN :kezdo_datum AND :vegzo_datum) OR (`vegzo_datum` BETWEEN :kezdo_datum AND :vegzo_datum) OR (`kezdo_datum` <= :kezdo_datum AND `vegzo_datum` >= :vegzo_datum));", nativeQuery = true)
    Integer hanyanFoglaltakMarAzIdopontokKozott(LocalDate kezdo_datum, LocalDate vegzo_datum, @Param("id") Integer id);

    @Query(value="SELECT * FROM foglalasok WHERE ((`kezdo_datum` BETWEEN :kezdo_datum AND :vegzo_datum) OR (`vegzo_datum` BETWEEN :kezdo_datum AND :vegzo_datum) OR (`kezdo_datum` <= :kezdo_datum AND `vegzo_datum` >= :vegzo_datum));", nativeQuery = true)
    List<Foglalasok> szabadAutok(LocalDate kezdo_datum, LocalDate vegzo_datum);
}
