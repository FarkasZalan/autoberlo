package com.example.demo.Controller;

import com.example.demo.entity.Auto;
import com.example.demo.entity.Foglalasok;
import com.example.demo.repository.AutoRepo;
import com.example.demo.repository.FoglalasokRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BerelTest {

    @Autowired
    AutoRepo autoRepo;
    @Autowired
    FoglalasokRepo foglalasokRepo;

    @Test
    @DisplayName("Jo auto oldalara vezet e")
    void autoBerleshezTest() {
        Integer id = 1;
        boolean vanIlyenId = false;
        List<Auto> autok = autoRepo.autokListaja();
        Auto keresendoAuto;
        for (Auto auto : autok) {
            if (Objects.equals(auto.getAuto_id(), id)) {
                vanIlyenId = true;
                break;
            }
        }
        if (vanIlyenId) {
            keresendoAuto = autoRepo.findById(id).get();
            assertEquals(id, keresendoAuto.getAuto_id(), "Nem jo auto oldalara iranyitott");
        } else {
            fail("Nincs ilyen tesztelheto id-val rendelkezo auto");
        }
    }

    @Test
    void attekinteshezTest() {
        LocalDate kezdo = LocalDate.parse("2023-06-11");
        LocalDate vegzo = LocalDate.parse("2023-06-14");
        Integer id = 1;
        Integer foglalasokSzamaAzIdoszakban = 0;
        List<Foglalasok> foglalasokListaja = foglalasokRepo.foglalasokListaja();
        boolean jolSzur = true;
        boolean vanIlyenId = false;

        List<Auto> autok = autoRepo.autokListaja();
        for (Auto auto : autok) {
            if (Objects.equals(auto.getAuto_id(), id)) {
                vanIlyenId = true;
                break;
            }
        }

        if (vanIlyenId) {
            foglalasokSzamaAzIdoszakban = foglalasokRepo.hanyanFoglaltakMarAzIdopontokKozott(kezdo, vegzo, id);
        } else {
            fail("Nincs ilyen tesztelheto id-val rendelkezo auto");
        }

        for (Foglalasok foglalasok : foglalasokListaja) {
            if (foglalasokSzamaAzIdoszakban == 0 && ((foglalasok.getKezdo_datum().isBefore(kezdo) && foglalasok.getVegzo_datum().isAfter(kezdo)) || (foglalasok.getKezdo_datum().isBefore(vegzo) && foglalasok.getVegzo_datum().isAfter(vegzo)))) {
                jolSzur = false;
                break;
            }
        }

        assertTrue(jolSzur, "Olyan autot is le tud foglalni ami az adott idoszakban mar le van mas altal foglalva");
    }
}