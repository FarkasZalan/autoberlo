package com.example.demo.Controller;

import com.example.demo.entity.Auto;
import com.example.demo.entity.Foglalasok;
import com.example.demo.repository.AutoRepo;
import com.example.demo.repository.FoglalasokRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
public class Berel {
    @Autowired
    private AutoRepo autoRepo;
    @Autowired
    private final FoglalasokRepo foglalasokRepo;

    private Foglalasok ujFoglalas;

    private static Auto jelenlegiAuto;

    public Berel(AutoRepo autoRepo, FoglalasokRepo foglalasokRepo) {
        this.autoRepo = autoRepo;
        this.foglalasokRepo = foglalasokRepo;
    }

    @RequestMapping(value = "/auto/{auto_id}")
    public String autoBerleshez(
            @PathVariable("auto_id") int id,
            Model model
    ) {
        jelenlegiAuto = autoRepo.findById(id).get();
        model.addAttribute("auto", jelenlegiAuto);

        return "auto";
    }

    @RequestMapping(value = "/attekinteshez")
    public String attekinteshez(
            @RequestParam("nev") String nev, @RequestParam("email") String email, @RequestParam("lakcim") String lakcim, @RequestParam("telefonszam") String telefonszam,
            @RequestParam("kezdo_datum") String kezdo_datum, @RequestParam("vegzo_datum") String vegzo_datum, @RequestParam("auto_id") String auto_id, RedirectAttributes redirectAttributes
            , Model model) {
        if (Objects.equals(kezdo_datum, "") || Objects.equals(vegzo_datum, "")) {
            redirectAttributes.addFlashAttribute("Uzenet", "Érvénytelen dátumot adtál meg!");
            return "redirect:/auto/" + auto_id;
        }
        LocalDate datum = LocalDate.now();
        LocalDate kezdo = LocalDate.parse(kezdo_datum);
        LocalDate vegzo = LocalDate.parse(vegzo_datum);
        if (datum.isAfter(kezdo)) {
            redirectAttributes.addFlashAttribute("Uzenet", "A foglalás kezdete nem lehet korábbi vagy a jelenlegi dátummal megegyező időpont!");
            return "redirect:/auto/" + auto_id;
        }
        if (kezdo.isAfter(vegzo)) {
            redirectAttributes.addFlashAttribute("Uzenet", "Nem lehet korábbi időpontra megadni a foglalás kezdetét, mint a végét!");
            return "redirect:/auto/" + auto_id;
        }

        Integer id = Integer.valueOf(auto_id);
        Integer foglalasokSzamaAzIdoszakban = foglalasokRepo.hanyanFoglaltakMarAzIdopontokKozott(kezdo, vegzo, id);
        if (foglalasokSzamaAzIdoszakban == 0) {
            Auto auto = autoRepo.findById(id).get();
            Integer napok_szama = Math.toIntExact(ChronoUnit.DAYS.between(kezdo, vegzo));
            if (napok_szama == 0) {
                napok_szama = 1;
            }
            Integer vegosszeg = auto.getBerles_dija() * napok_szama;
            ujFoglalas = new Foglalasok(email, nev, lakcim, telefonszam, vegosszeg, auto.getAuto_rendszam(), kezdo, vegzo, id, "aktív");
            model.addAttribute("felhasznalo", ujFoglalas);
            model.addAttribute("auto", jelenlegiAuto);
            return "/osszegzes";
        } else {
            redirectAttributes.addFlashAttribute("Uzenet", "Ezt a kocsit már lefoglalták az adott időintervallumban!");
            return "redirect:/auto/" + auto_id;
        }
    }

    @RequestMapping(value = "/foglalas_process")
    public String foglalas_process(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("Uzenet", "Sikeres foglalás!");
        foglalasokRepo.save(ujFoglalas);

        return "redirect:/";
    }
}
