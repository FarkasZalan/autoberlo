package com.example.demo.Controller;

import com.example.demo.entity.Auto;
import com.example.demo.entity.Foglalasok;
import com.example.demo.repository.AutoRepo;
import com.example.demo.repository.FoglalasokRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminFunkciokTest {

    @Autowired
    AutoRepo autoRepo;
    @Autowired
    FoglalasokRepo foglalasokRepo;

    @Test
    @DisplayName("Kilistazza az osszes leadott foglalast")
    void foglalasokTest() {
        List<Foglalasok> foglalasokLista = foglalasokRepo.foglalasokListaja();
        assertEquals(foglalasokLista.size(), foglalasokRepo.findAll().size(), "Nem jol listazza ki a leadott foglalasokat");
        //Azert a meretuket hasonlitom ossze, mert nem lehet ugyanaz a 2 lista, hiszen a query az rendezi is a listat a sima findAll() pedig nem
    }

    @Test
    @DisplayName("Kilistazza az osszes autot az adminnak akar elerheto akar innaktiv")
    void autokTest() {
        List<Auto> autokListaja = autoRepo.autokListajaAdmin();
        assertEquals(autokListaja.size(), autoRepo.findAll().size(), "Nem jol listazza ki az adminnak az autoit");
        //Azert a meretuket hasonlitom ossze, mert nem lehet ugyanaz a 2 lista, hiszen a query az rendezi is a listat a sima findAll() pedig nem
    }

    @Test
    @DisplayName("Az adott auto innaktiv lesz")
    void auto_innaktivalasaTest() {
        Integer id = 2;
        boolean vanIlyenId = false;
        String eredetiAllapot = "";
        Auto innaktivalando = new Auto();
        List<Auto> autok = autoRepo.autokListajaAdmin();
        for (Auto auto : autok) {
            if (Objects.equals(auto.getAuto_id(), id)) {
                vanIlyenId = true;
                innaktivalando = autoRepo.findById(id).get();
                break;
            }
        }

        if (vanIlyenId) {
            eredetiAllapot = innaktivalando.getInnaktiv();
            if (eredetiAllapot.equals("elerheto")) {
                autoRepo.autoElerhetosege("innaktív", id);
                innaktivalando = autoRepo.findById(id).get();
                assertEquals("innaktív", innaktivalando.getInnaktiv());
                autoRepo.autoElerhetosege(eredetiAllapot, id);
            } else {
                autoRepo.autoElerhetosege("elerheto", id);
                innaktivalando = autoRepo.findById(id).get();
                assertEquals("elerheto", innaktivalando.getInnaktiv());
                autoRepo.autoElerhetosege(eredetiAllapot, id);
            }


        } else {
            fail("Nincs ilyen tesztelheto id-val rendelkezo auto");
        }
    }

    @Test
    @DisplayName("Az adott auto oldalara iranyit ahol szerkeszthetoek az adatai")
    void autoSzerkesztesTest() {
        Integer id = 1;
        boolean vanIlyenId = false;
        List<Auto> autok = autoRepo.autokListajaAdmin();
        Auto modositandoAuto;
        for (Auto auto : autok) {
            if (Objects.equals(auto.getAuto_id(), id)) {
                vanIlyenId = true;
                break;
            }
        }
        if (vanIlyenId) {
            modositandoAuto = autoRepo.findById(id).get();
            assertEquals(id, modositandoAuto.getAuto_id(), "Nem jo auto oldalara iranyitott");
        } else {
            fail("Nincs ilyen tesztelheto id-val rendelkezo auto");
        }
    }

    @Test
    @DisplayName("Az adott auto adatai frissulnek")
    void auto_szerkeszteseTest() {
        Integer id = 1;
        boolean vanIlyenId = false;
        List<Auto> autok = autoRepo.autokListajaAdmin();
        Auto modositandoAuto;
        for (Auto auto : autok) {
            if (Objects.equals(auto.getAuto_id(), id)) {
                vanIlyenId = true;
                break;
            }
        }
        if (vanIlyenId) {
            modositandoAuto = autoRepo.findById(id).get();
            autoRepo.alapadatFrissites(id, modositandoAuto.getAuto_nev(), "REND - 123", modositandoAuto.getBerles_dija(), modositandoAuto.getEvjarat(), modositandoAuto.getInnaktiv(),
                    modositandoAuto.getMarka(), modositandoAuto.getMotor_kobcenti(), modositandoAuto.getTipus(), modositandoAuto.getUlesek_szama());
            Auto osszehasonlitando = autoRepo.findById(id).get();
            assertEquals("REND - 123", osszehasonlitando.getAuto_rendszam(), "Nem jol modosult az auto egyik alap adata");

            autoRepo.alapadatFrissites(id, modositandoAuto.getAuto_nev(), modositandoAuto.getAuto_rendszam(), modositandoAuto.getBerles_dija(), modositandoAuto.getEvjarat(), modositandoAuto.getInnaktiv(),
                    modositandoAuto.getMarka(), modositandoAuto.getMotor_kobcenti(), modositandoAuto.getTipus(), modositandoAuto.getUlesek_szama());
        } else {
            fail("Nincs ilyen tesztelheto id-val rendelkezo auto");
        }
    }

    @Test
    @DisplayName("Uj autot lehet letrehozni")
    void auto_hozzaadasaTest() {
        Auto ujAutoTest = new Auto("TEST - 123", "Punto Teszter", "Fiat", "Benzin", 900, 1997, 4, 6700, "", "elerheto");
        int eddigiLetszam = autoRepo.autokListajaAdmin().size();
        autoRepo.save(ujAutoTest);
        assertEquals((eddigiLetszam + 1), autoRepo.autokListajaAdmin().size(), "Nem sikerult a letrehozas");
        autoRepo.delete(ujAutoTest);
    }
}