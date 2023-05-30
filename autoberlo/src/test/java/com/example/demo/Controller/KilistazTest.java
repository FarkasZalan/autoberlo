package com.example.demo.Controller;

import com.example.demo.entity.Auto;
import com.example.demo.entity.Foglalasok;
import com.example.demo.repository.AutoRepo;
import com.example.demo.repository.FoglalasokRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KilistazTest {

    @Autowired
    private AutoRepo autoRepo;
    @Autowired
    private FoglalasokRepo foglalasokRepo;

    @Test
    @DisplayName("Fooldalon levo elerheto autok listazasanak tesztelese")
    void fooldalTest () {
        List<Auto> autokListaTest = autoRepo.autokListaja();
        boolean innaktivAutokatIsListaz = false;
        for (Auto auto : autokListaTest) {
            if (auto.getInnaktiv().equals("innaktiv")) {
                innaktivAutokatIsListaz = true;
                break;
            }
        }
        assertNotNull(autokListaTest, "Null lett az autok listaja");
        assertFalse(innaktivAutokatIsListaz, "Innaktiv autok is ki lettek listazva");

    }

    @Test
    @DisplayName("Szures elerhetoseg szerint a megadott idointervallumok kozott")
    void szuresTest() {
        LocalDate kezdo = LocalDate.parse("2023-06-01");
        LocalDate vegzo = LocalDate.parse("2023-06-11");
        List<Foglalasok> foglalasokListaja = foglalasokRepo.szabadAutok(kezdo, vegzo);

        boolean jolSzur = true;
        for (Foglalasok foglalasok : foglalasokListaja) {
            if ((foglalasok.getKezdo_datum().isBefore(kezdo) && foglalasok.getVegzo_datum().isAfter(kezdo)) || (foglalasok.getKezdo_datum().isBefore(vegzo) && foglalasok.getVegzo_datum().isAfter(vegzo))) {
                jolSzur = false;
                break;
            }
        }

        assertTrue(jolSzur, "Olyanokat foglalasokat is kiszur amik az adott idoszakba nem esnek bele");
    }
}