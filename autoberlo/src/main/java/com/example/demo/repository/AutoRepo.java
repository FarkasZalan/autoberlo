package com.example.demo.repository;

import com.example.demo.entity.Auto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AutoRepo extends JpaRepository<Auto, Integer> {

    @Query(value = "SELECT * FROM `auto` WHERE `innaktiv` != 'innaktiv' ORDER BY `auto_id` DESC;", nativeQuery = true)
    List<Auto> autokListaja();

    @Query(value = "SELECT * FROM `auto` ORDER BY `auto_id` DESC;", nativeQuery = true)
    List<Auto> autokListajaAdmin();


    @Modifying
    @Query(value = "update auto set kep= :kep where auto_id = :auto_id", nativeQuery = true)
    void autoKepe(@Param("kep") String kep, @Param("auto_id") Integer auto_id);

    @Modifying
    @Query(value = "update auto set innaktiv= :innaktiv where auto_id = :auto_id", nativeQuery = true)
    void autoElerhetosege(@Param("innaktiv") String innaktiv, @Param("auto_id") Integer auto_id);

    @Modifying
    @Query(value = "update auto set auto_nev= :auto_nev, auto_rendszam= :auto_rendszam, berles_dija= :berles_dija, evjarat= :evjarat, innaktiv= :innaktiv, marka= :marka, motor_kobcenti= :motor_kobcenti, tipus= :tipus, ulesek_szama= :ulesek_szama where auto_id = :auto_id", nativeQuery = true)
    void alapadatFrissites(@Param("auto_id") Integer auto_id, @Param("auto_nev") String auto_nev, @Param("auto_rendszam") String auto_rendszam, @Param("berles_dija") Integer berles_dija, @Param("evjarat") Integer evjarat,
                           @Param("innaktiv") String innaktiv, @Param("marka") String marka, @Param("motor_kobcenti") Integer motor_kobcenti, @Param("tipus") String tipus, @Param("ulesek_szama") Integer ulesek_szama);
}
